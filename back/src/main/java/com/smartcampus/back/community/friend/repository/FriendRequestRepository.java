package com.smartcampus.back.community.friend.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.friend.entity.FriendRequest;
import com.smartcampus.back.community.friend.entity.enums.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 친구 요청 저장소
 * <p>
 * 사용자 간 친구 요청 상태를 저장하고 관리합니다.
 * 요청 중복 방지 및 상태 기반 필터링 처리를 지원합니다.
 * </p>
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    /**
     * 특정 발신자와 수신자 간의 친구 요청 존재 여부 확인
     *
     * @param sender 요청 보낸 사용자
     * @param receiver 요청 받은 사용자
     * @return 요청 객체(Optional)
     */
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    /**
     * 수신자 기준으로 받은 친구 요청 목록 조회
     *
     * @param receiver 수신자
     * @return 수신자가 받은 친구 요청 목록
     */
    List<FriendRequest> findAllByReceiver(User receiver);

    /**
     * 발신자 기준으로 보낸 친구 요청 목록 조회
     *
     * @param sender 발신자
     * @return 발신자가 보낸 친구 요청 목록
     */
    List<FriendRequest> findAllBySender(User sender);

    /**
     * 요청 상태로 필터링된 친구 요청 목록 조회
     *
     * @param receiver 요청 수신자
     * @param status 요청 상태 (PENDING, ACCEPTED, REJECTED 등)
     * @return 상태 기준 요청 목록
     */
    List<FriendRequest> findAllByReceiverAndStatus(User receiver, FriendRequestStatus status);
}
