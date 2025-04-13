package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notification 엔티티를 위한 JPA Repository 인터페이스입니다.
 * 사용자별 알림 목록 조회, 읽지 않은 알림 수 조회 등 다양한 알림 관련 쿼리를 제공합니다.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 특정 사용자의 알림 목록을 최신순으로 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 알림 리스트
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * 특정 사용자의 읽지 않은 알림 수를 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 읽지 않은 알림 개수
     */
    long countByUserIdAndReadFalse(Long userId);

    /**
     * 특정 사용자의 읽지 않은 알림 목록을 반환합니다.
     * (일괄 읽음 처리용)
     *
     * @param userId 사용자 ID
     * @return 읽지 않은 알림 리스트
     */
    List<Notification> findByUserIdAndReadFalse(Long userId);
}
