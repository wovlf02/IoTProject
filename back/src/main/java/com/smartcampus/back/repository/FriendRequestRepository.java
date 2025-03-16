package com.smartcampus.back.repository;

import com.smartcampus.back.entity.FriendRequest;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FriendRequestRepository 인터페이스
 *
 * friend_requests 테이블과 매핑된 FriendRequest 엔티티의 데이터 접근 계층
 * 친구 요청 보내기, 수락, 거절, 조회 기능 포함
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    /**
     * 특정 사용자가 보낸 친구 요청 목록 조회
     *
     * sender_id를 기준으로 해당 사용자가 보낸 친구 요청 목록을 반환
     *
     * @param sender 친구 요청을 보낸 사용자
     * @return 사용자가 보낸 친구 요청 목록
     */
    List<FriendRequest> findBySender(User sender);

    /**
     * 특정 사용자가 받은 친구 요청 목록 조회
     *
     * receiver_id를 기준으로 해당 사용자가 받은 친구 요청 모곡을 반환
     *
     * @param receiver 친구 요청을 받은 사용자
     * @return 사용자가 받은 친구 요청 목록
     */
    List<FriendRequest> findByReceiver(User receiver);

    /**
     * 특정 사용자 간의 친구 요청 여부 확인
     *
     * sender_id와 receiver_id를 기준으로 조회
     * 친구 요청이 존재하는지 확인하는 용도
     *
     * @param sender 친구 요청을 보낸 사용자
     * @param receiver 친구 요청을 받은 사용자
     * @return Optional<FriendRequest> 객체 (요청이 존재하면 FriendRequest, 없으면 empty)
     */
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    /**
     * 특정 사용자가 받은 대기 중인(friend_requests.status = 'pending') 친구 요청 개수 조회
     *
     * receiver_id를 기준으로 해당 사용자가 받은 대기 중인 요청 개수를 반환
     *
     * @param receiver 친구 요청을 받은 사용자
     * @param status 대기 중인 요청의 상태
     * @return 사용자가 받은 대기 중인 친구 요청 수
     */
    long countByReceiverAndStatus(User receiver, FriendRequest.RequestStatus status);

    /**
     * 특정 사용자 간의 친구 요청 삭제
     * 
     * sender_id와 receiver_id를 기반으로 친구 요청 삭제 (요청 취소 또는 거절 시 사용)
     * 
     * @param sender 친구 요청을 보낸 사용자
     * @param receiver 친구 요청을 받은 사용자
     */
    void deleteBySenderAndReceiver(User sender, User receiver);
}
