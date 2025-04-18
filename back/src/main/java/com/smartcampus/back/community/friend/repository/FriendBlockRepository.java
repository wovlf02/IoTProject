package com.smartcampus.back.community.friend.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.friend.entity.FriendBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 차단 정보에 접근하는 JPA Repository
 */
@Repository
public interface FriendBlockRepository extends JpaRepository<FriendBlock, Long> {

    /**
     * 특정 사용자가 차단한 사용자 목록을 조회합니다.
     *
     * @param blocker 차단한 사용자
     * @return 차단된 사용자 엔티티 목록
     */
    List<FriendBlock> findByBlocker(User blocker);

    /**
     * 특정 사용자가 특정 사용자를 차단했는지 확인합니다.
     *
     * @param blocker 차단한 사용자
     * @param blocked 차단당한 사용자
     * @return FriendBlock 엔티티 (Optional)
     */
    Optional<FriendBlock> findByBlockerAndBlocked(User blocker, User blocked);

    /**
     * 차단 정보 삭제
     *
     * @param blocker 차단한 사용자
     * @param blocked 차단당한 사용자
     */
    void deleteByBlockerAndBlocked(User blocker, User blocked);
}
