package com.smartcampus.back.community.friend.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.friend.dto.response.BlockedFriendListResponse;
import com.smartcampus.back.community.friend.dto.response.FriendListResponse;
import com.smartcampus.back.community.friend.dto.response.FriendRequestListResponse;
import com.smartcampus.back.community.friend.entity.Friend;
import com.smartcampus.back.community.friend.entity.FriendBlock;
import com.smartcampus.back.community.friend.entity.FriendRequest;
import com.smartcampus.back.community.friend.repository.FriendBlockRepository;
import com.smartcampus.back.community.friend.repository.FriendRepository;
import com.smartcampus.back.community.friend.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FriendQueryService
 * <p>
 * 친구 관계 및 친구 요청, 차단 목록 등 사용자 기반의 친구 관련 정보 조회 기능을 담당합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FriendQueryService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendBlockRepository friendBlockRepository;

    /**
     * 현재 사용자의 친구 목록을 조회합니다.
     *
     * @param user 현재 로그인한 사용자
     * @return 친구 목록 DTO 리스트
     */
    public List<FriendListResponse> getFriendList(User user) {
        List<Friend> friends = friendRepository.findByUser(user);
        return friends.stream()
                .map(friend -> new FriendListResponse(friend.getFriendUser()))
                .collect(Collectors.toList());
    }

    /**
     * 현재 사용자가 받은 친구 요청 목록을 조회합니다.
     *
     * @param user 현재 로그인한 사용자
     * @return 친구 요청 목록 DTO 리스트
     */
    public List<FriendRequestListResponse> getReceivedFriendRequests(User user) {
        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceiver(user);
        return receivedRequests.stream()
                .map(FriendRequestListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 현재 사용자가 차단한 사용자 목록을 조회합니다.
     *
     * @param user 현재 로그인한 사용자
     * @return 차단된 사용자 목록 DTO 리스트
     */
    public List<BlockedFriendListResponse> getBlockedUsers(User user) {
        List<FriendBlock> blocks = friendBlockRepository.findByBlocker(user);
        return blocks.stream()
                .map(block -> new BlockedFriendListResponse(block.getBlockedUser()))
                .collect(Collectors.toList());
    }
}
