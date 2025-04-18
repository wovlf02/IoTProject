package com.smartcampus.back.community.friend.repository;

import com.smartcampus.back.community.friend.entity.Friend;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 친구 관계 저장소 인터페이스
 * <p>
 * 사용자 간 수락된 친구 관계(Friend 엔티티)를 저장 및 조회하는 JPA 리포지토리입니다.
 * 양방향 친구 관계를 처리하며, 사용자 기준으로 모든 친구 목록을 조회할 수 있습니다.
 * </p>
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    /**
     * 주어진 사용자 A가 친구로 등록한 목록 조회
     *
     * @param userA 기준 사용자 A
     * @return 사용자 A가 등록한 친구 목록
     */
    List<Friend> findAllByUserA(User userA);

    /**
     * 주어진 사용자 B가 친구로 등록된 목록 조회
     *
     * @param userB 기준 사용자 B
     * @return 사용자 B가 친구로 등록된 목록
     */
    List<Friend> findAllByUserB(User userB);

    /**
     * 두 사용자 간 친구 관계가 존재하는지 확인
     *
     * @param userA 친구 관계의 한쪽 사용자
     * @param userB 친구 관계의 다른쪽 사용자
     * @return 친구 관계 존재 여부
     */
    Optional<Friend> findByUserAAndUserB(User userA, User userB);

    /**
     * 두 사용자 간의 역방향 친구 관계도 체크 (상호 관계 고려)
     *
     * @param userB 친구 관계의 한쪽 사용자
     * @param userA 친구 관계의 다른쪽 사용자
     * @return 친구 관계 존재 여부
     */
    Optional<Friend> findByUserBAndUserA(User userB, User userA);
}
