package com.smartcampus.back.repository;

import com.smartcampus.back.entity.FriendRequest;
import com.smartcampus.back.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FriendRequestRepository (친구 요청 Repository)
 * friend_requests 테이블과 매핑됨
 * 친구 요청 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {

    /**
     * 특정 사용자가 받은 친구 요청 목록 조회
     * @param receiver 친구 요청을 받은 사용자 (User 엔티티)
     * @return 해당 사용자가 받은 친구 요청 목록
     */
    List<FriendRequest> findByReceiver(User receiver);

    /**
     * 특정 사용자가 보낸 친구 요청 목록 조회
     * @param sender 친구 요청을 보낸 사용자 (User 엔티티)
     * @return 해당 사용자가 보낸 친구 요청 목록
     */
    List<FriendRequest> findBySender(User sender);

    /**
     * 특정 사용자 간 친구 요청 여부 확인
     * @param sender 친구 요청을 보낸 사용자 (User 엔티티)
     * @param receiver 친구 요청을 받은 사용자 (User 엔티티)
     * @return 친구 요청이 존재하면 Optional에 포함된 객체 반환, 없으면 빈 Optional 반환
     */
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    /**
     * 특정 사용자 간 친구 요청 삭제 (요청 취소 또는 거절)
     * @param sender 친구 요청을 보낸 사용자 (User 엔티티)
     * @param receiver 친구 요청을 받은 사용자 (User 엔티티)
     */
    void deleteBySenderAndReceiver(User sender, User receiver);
}
