package com.smartcampus.back.community.friend.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.community.friend.dto.response.FriendSimpleResponse;
import com.smartcampus.back.community.friend.entity.Friend;
import com.smartcampus.back.community.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * FriendService
 * <p>
 * 친구 삭제, 친구 여부 확인 등 친구 관계의 유지/해제 관련 기능을 담당합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    /**
     * 친구 관계를 삭제합니다.
     * A → B, B → A 둘 중 어떤 관계든 삭제합니다.
     *
     * @param user     현재 사용자
     * @param friendId 삭제할 친구의 ID
     * @return 처리 메시지 응답
     */
    @Transactional
    public FriendSimpleResponse deleteFriend(User user, Long friendId) {
        Optional<Friend> directRelation = friendRepository.findByUserAAndUserB(user.getId(), friendId);
        Optional<Friend> reverseRelation = friendRepository.findByUserBAndUserA(user.getId(), friendId);

        if (directRelation.isPresent()) {
            friendRepository.delete(directRelation.get());
        } else if (reverseRelation.isPresent()) {
            friendRepository.delete(reverseRelation.get());
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND, "친구 관계가 존재하지 않습니다.");
        }

        return FriendSimpleResponse.builder()
                .message("친구가 삭제되었습니다.")
                .targetUserId(friendId)
                .build();
    }

    /**
     * 두 사용자 간 친구 관계 여부를 확인합니다.
     *
     * @param userA 사용자 A
     * @param userB 사용자 B
     * @return true: 친구 관계 존재 / false: 없음
     */
    @Transactional(readOnly = true)
    public boolean areFriends(User userA, User userB) {
        return friendRepository.findByUserAAndUserB(userA.getId(), userB.getId()).isPresent() ||
                friendRepository.findByUserBAndUserA(userA.getId(), userB.getId()).isPresent();
    }
}
