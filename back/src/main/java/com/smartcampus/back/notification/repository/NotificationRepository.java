package com.smartcampus.back.notification.repository;

import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notification 관련 데이터베이스 접근을 담당하는 리포지토리 인터페이스
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 특정 사용자의 알림 목록을 생성일 기준 내림차순으로 조회
     *
     * @param user 사용자
     * @return 알림 목록
     */
    List<Notification> findByReceiverOrderByCreatedAtDesc(User user);

    /**
     * 읽지 않은 알림 개수 조회
     *
     * @param user 사용자
     * @return 읽지 않은 알림 수
     */
    Long countByReceiverAndIsReadFalse(User user);
}
