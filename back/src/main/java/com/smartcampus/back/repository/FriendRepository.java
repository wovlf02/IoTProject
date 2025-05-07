package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    /**
     * 특정 사용자의 친구 목록 조회
     *
     * @param userId 사용자 ID
     * @return 친구 리스트
     */
    List<Friend> findByUserId(Long userId);

    /**
     * 사용자 간 친구 관계 존재 여부 확인
     *
     * @param userId 사용자 ID
     * @param friendId 친구 ID
     * @return 친구 관계 존재 여부
     */
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
