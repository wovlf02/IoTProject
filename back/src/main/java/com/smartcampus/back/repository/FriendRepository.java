package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Friend;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FriendRepository 인터페이스
 *
 * friends 테이블과 매핑된 Friend 엔티티의 데이터 접근 계층
 * 친구 추가, 삭제, 조회 기능 포함
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    /**
     * 특정 사용자의 모든 친구 목록 조회
     *
     * user_id를 기준으로 해당 사용자의 친구 목록을 반환
     *
     * @param user 사용자 객체
     * @return 사용자의 친구 목록
     */
    List<Friend> findByUser(User user);

    /**
     * 특정 사용자가 특정 친구와 친구 관계인지 확인
     * 
     * user_id와 friend_id를 기준으로조회
     * 
     * @param user 사용자 객체
     * @param friend 친구 객체
     * @return Optional<Friend> 객체 (친구 관계가 존재하면 Friend, 없으면 empty)
     */
    Optional<Friend> findByUserAndFriend(User user, Friend friend);

    /**
     * 특정 사용자의 친구 개수 조회
     * 
     * user_id를 기준으로 해당 사용자의 친구 수를 반환
     * 
     * @param user 사용자 객체
     * @return 사용자의 친구 수
     */
    long countByUser(User user);

    /**
     * 특정 친구 관계 삭제 (친구 삭제 기능)
     * 
     * user_id와 friend_id를 기반으로 친구 관계 삭제
     * 
     * @param user 사용자 객체
     * @param friend 친구 객체
     */
    void deleteByUserAndFriend(User user, Friend friend);
}
