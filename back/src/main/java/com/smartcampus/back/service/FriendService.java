package com.smartcampus.back.service;

import com.smartcampus.back.dto.FriendResponse;
import com.smartcampus.back.dto.ReportRequest;
import com.smartcampus.back.entity.*;
import com.smartcampus.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 친구 관리 서비스
 * 친구 요청, 친구 목록 조회, 친구 수락/거절, 친구 삭제, 친구 차단/신고 기능 처리
 */
@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    // ======================= 1. 친구 요청 관련 기능 =========================

    /**
     * 친구 요청 보내기
     * @param request 친구 요청 DTO
     * @return 요청 결과 메시지
     */
    @Transactional
    public String sendFriendRequest(FriendRequestDto request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("요청을 보낸 사용자를 찾을 수 없습니다."));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("요청을 받은 사용자를 찾을 수 없습니다."));

        if(friendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            return "이미 친구 요청을 보냈습니다.";
        }

        if(friendRepository.existsByUserAndFriend(sender, receiver)) {
            return "이미 친구 상태입니다.";
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status("pending")
                .build();

        friendRequestRepository.save(friendRequest);
        return "친구 요청이 성공적으로 전송되었습니다.";
    }

    /**
     * 받은 친구 요청 목록 조회
     * @param userId 사용자 ID
     * @return 받은 친구 요청 목록
     */
    public List<FriendRequestResponse> getReceivedFriendRequests(Long userId) {
        List<FriendRequest> requests = friendRequestRepository.findByReceiverIdAndStatus(userId, "pending");
        return requests.stream().map(FriendReqeustResponse::new).collect(Collectors.toList());
    }

    /**
     * 친구 요청 수락
     * @param request 친구 요청 처리 DTO
     * @return 처리 결과 메시지
     */
    @Transactional
    public String acceptFriendReqeust(FriendReqeustDto request) {
        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(
                request.getSenderId(), request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();

        // 친구 관계 저장
        Friend friendship1 = Friend.builder().user(sender).friend(receiver).build();
        Friend friendship2 = Friend.builder().user(receiver).friend(sender).build();

        friendRepository.save(friendship1);
        friendRepository.save(friendship2);

        // 친구 요청 삭제
        friendRequestRepository.delete(friendRequest);
        return "친구 요청이 수락되었습니다.";
    }

    /**
     * 친구 요청 거절
     * @param request 친구 요청 처리 DTO
     * @return 처리 결과 메시지
     */
    @Transactional
    public String rejectFriendRequest(FriendRequestDto request) {
        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(
                request.getSenderId(), request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        // 친구 요청 삭제
        friendRequestRepository.delete(friendRequest);
        return "친구 요청을 거절하였습니다.";
    }

    // ======================= 2. 친구 목록 관리 =========================

    /**
     * 친구 목록 조회
     * @param userId 사용자 ID
     * @return 친구 목록
     */
    public List<FriendResponse> getFriendList(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);
        return friends.stream().map(FriendResponse::new).collect(Collectors.toList());
    }

    /**
     * 친구 삭제
     * @param userId 사용자 ID
     * @param friendId 삭제할 친구 ID
     * @return 처리 결과 메시지
     */
    @Transactional
    public String deleteFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 친구를 찾을 수 없습니다."));

        Friend friendship1 = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        Friend friendship2 = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        friendRepository.delete(friendship1);
        friendRepository.delete(friendship2);

        return "친구가 삭제되었습니다.";
    }

    // ======================= 3. 친구 차단 및 신고 =========================

    /**
     * 친구 차단
     * @param userId 사용자 ID
     * @param friendId 차단할 친구 ID
     * @return 처리 결과 메시지
     */
    @Transactional
    public String blockFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("차단할 친구를 찾을 수 없습니다."));

        // 기존 친구 관계가 있다면 삭제
        friendRepository.findByUserAndFriend(user, friend)
                .ifPresent(friendRepository::delete);
        friendRepository.findByUserAndFriend(friend, user)
                .ifPresent(friendRepository::delete);

        return "해당 사용자를 차단하였습니다.";
    }

    /**
     * 친구 신고
     * @param userId 신고하는 사용자 ID
     * @param friendId 신고 대상 친구 ID
     * @param request 신고 요청 DTO
     * @return 신고 결과 메시지
     */
    public String reportFriend(Long userId, Long friendId, ReportRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("신고할 친구를 찾을 수 없습니다."));

        // 신고 처리 로직 (추후 DB 저장 가능)
        return "해당 사용자를 신고하였습니다.";
    }
}
