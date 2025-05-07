package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 특정 사용자의 알림 목록을 생성일 내림차순으로 조회
     *
     * @param receiverId 수신자 ID
     * @return 알림 리스트
     */
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

    /**
     * 특정 사용자의 읽지 않은 알림 수 조회
     *
     * @param receiverId 수신자 ID
     * @return 읽지 않은 알림 수
     */
    long countByReceiverIdAndIsReadFalse(Long receiverId);
}
