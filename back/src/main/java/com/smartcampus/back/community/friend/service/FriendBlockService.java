package com.smartcampus.back.community.friend.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.friend.entity.FriendBlock;
import com.smartcampus.back.community.friend.repository.FriendBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * FriendBlockService
 * <p>
 * 사용자 간의 친구 차단 및 차단 해제 기능을 제공하는 서비스 클래스입니다.
 * 단방향 차단 관계를 저장/삭제하며, 차단 여부 확인도 지원합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FriendBlockService {

    private final FriendBlockRepository friendBlockRepository;

    /**
     * 특정 사용자를 차단합니다.
     *
     * @param blocker       차단을 요청하는 사용자
     * @param targetToBlock 차단 대상 사용자
     */
    @Transactional
    public void blockUser(User blocker, User targetToBlock) {
        // 이미 차단된 상태인지 확인
        if (isBlocked(blocker, targetToBlock)) {
            return;
        }

        FriendBlock block = FriendBlock.builder()
                .blocker(blocker)
                .blockedUser(targetToBlock)
                .build();

        friendBlockRepository.save(block);
    }

    /**
     * 특정 사용자의 차단을 해제합니다.
     *
     * @param blocker       차단을 해제하려는 사용자
     * @param targetToUnblock 차단 해제 대상 사용자
     */
    @Transactional
    public void unblockUser(User blocker, User targetToUnblock) {
        Optional<FriendBlock> block = friendBlockRepository.findByBlockerAndBlockedUser(blocker, targetToUnblock);
        block.ifPresent(friendBlockRepository::delete);
    }

    /**
     * 해당 사용자가 특정 사용자를 차단 중인지 확인합니다.
     *
     * @param blocker       차단 여부를 확인하려는 사용자
     * @param target        확인 대상 사용자
     * @return true: 차단 중, false: 차단 아님
     */
    @Transactional(readOnly = true)
    public boolean isBlocked(User blocker, User target) {
        return friendBlockRepository.existsByBlockerAndBlockedUser(blocker, target);
    }
}
