package com.smartcampus.back.community.friend.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.community.friend.dto.request.FriendAcceptDto;
import com.smartcampus.back.community.friend.dto.request.FriendRequestDto;
import com.smartcampus.back.community.friend.dto.response.FriendSimpleResponse;
import com.smartcampus.back.community.friend.entity.Friend;
import com.smartcampus.back.community.friend.entity.FriendRequest;
import com.smartcampus.back.community.friend.entity.enums.FriendRequestStatus;
import com.smartcampus.back.community.friend.repository.FriendRepository;
import com.smartcampus.back.community.friend.repository.FriendRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 친구 요청 처리 서비스
 * <p>
 * 친구 요청 생성, 수락, 거절 등 상태 기반 처리와 중복 요청 방지를 담당합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;

    /**
     * 친구 요청을 보냅니다.
     *
     * @param sender  요청을 보낸 사용자
     * @param request 요청 대상 사용자 ID
     * @return 처리 결과 메시지 응답
     */
    @Transactional
    public FriendSimpleResponse sendFriendRequest(User sender, FriendRequestDto request) {
        if (sender.getId().equals(request.getTargetUserId())) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "자기 자신에게는 친구 요청을 보낼 수 없습니다.");
        }

        Optional<FriendRequest> existingRequest =
                friendRequestRepository.findBySenderAndReceiver(sender.getId(), request.getTargetUserId());

        if (existingRequest.isPresent() && existingRequest.get().getStatus() == FriendRequestStatus.PENDING) {
            throw new CustomException(ErrorCode.DUPLICATE_REQUEST, "이미 친구 요청을 보냈습니다.");
        }

        // 친구 이미 되어 있는지 확인
        boolean alreadyFriends = friendRepository.existsByUsers(sender.getId(), request.getTargetUserId());
        if (alreadyFriends) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "이미 친구인 사용자입니다.");
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(User.builder().id(request.getTargetUserId()).build()) // 프록시 객체로 처리
                .status(FriendRequestStatus.PENDING)
                .build();

        friendRequestRepository.save(friendRequest);

        return FriendSimpleResponse.builder()
                .message("친구 요청이 전송되었습니다.")
                .targetUserId(request.getTargetUserId())
                .build();
    }

    /**
     * 친구 요청을 수락합니다.
     *
     * @param user    요청을 수락하는 사용자
     * @param request 수락 요청 DTO
     * @return 처리 결과 메시지
     */
    @Transactional
    public FriendSimpleResponse acceptFriendRequest(User user, FriendAcceptDto request) {
        FriendRequest friendRequest = friendRequestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "친구 요청이 존재하지 않습니다."));

        if (!friendRequest.getReceiver().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION, "요청 수락 권한이 없습니다.");
        }

        if (friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "이미 처리된 요청입니다.");
        }

        friendRequest.accept();

        // 친구 관계 생성
        Friend friend = Friend.builder()
                .userA(friendRequest.getSender())
                .userB(friendRequest.getReceiver())
                .build();

        friendRepository.save(friend);

        return FriendSimpleResponse.builder()
                .message("친구 요청을 수락했습니다.")
                .targetUserId(friendRequest.getSender().getId())
                .build();
    }

    /**
     * 친구 요청을 거절합니다.
     *
     * @param user    요청을 거절하는 사용자
     * @param request 거절 요청 DTO
     * @return 처리 결과 메시지
     */
    @Transactional
    public FriendSimpleResponse rejectFriendRequest(User user, FriendAcceptDto request) {
        FriendRequest friendRequest = friendRequestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "친구 요청이 존재하지 않습니다."));

        if (!friendRequest.getReceiver().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION, "요청 거절 권한이 없습니다.");
        }

        if (friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "이미 처리된 요청입니다.");
        }

        friendRequest.reject();

        return FriendSimpleResponse.builder()
                .message("친구 요청을 거절했습니다.")
                .targetUserId(friendRequest.getSender().getId())
                .build();
    }
}
