package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Friend;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FriendRepository (친구 목록 Repository)
 * friends 테이블과 매핑됨
 * 친구 관계 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    /**
     * 특정 사용자의 친구 목록 조회
     * @param user 친구 목록을 조회할 사용자 (User 엔티티)
     * @return 해당 사용자의 친구 목록
     */
    List<Friend> findByUser(User user);

    /**
     * 특정 사용자의 특정사용자가 친구인지 확인
     * @param user 사용자의 ID (User 엔티티)
     * @param friend 친구의 ID (User 엔티티)
     * @return 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByUserAndFriend(User user, User friend);

    /**
     * 특정 사용자와 특정 친구의 친구 관계 삭제
     * @param user 친구 관계를 삭제할 사용자 (User 엔티티)
     * @param friend 삭제할 친구 (User 엔티티)
     */
    void deleteByUserAndFriend(User user, User friend);
}
