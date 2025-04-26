package com.smartcampus.back.notification.repository;

import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NotificationRepository
 * <p>
 * 알림(Notification) 엔티티를 관리하는 JPA Repository입니다.
 * 기본 CRUD 기능 외에도 사용자별 알림 목록 조회, 읽지 않은 알림 개수 조회, 알림 타입 필터링 기능을 제공합니다.
 * </p>
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 특정 사용자의 알림 목록을 최신순으로 조회합니다. (Cursor 기반 무한 스크롤 지원)
     *
     * @param user     알림을 받을 사용자
     * @param pageable 페이지네이션 정보 (Cursor 기반 Slice)
     * @return 알림 목록 Slice
     */
    Slice<Notification> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    /**
     * 특정 사용자의 알림 목록 중 지정된 타입만 조회합니다.
     *
     * @param user     알림을 받을 사용자
     * @param type     필터링할 알림 타입
     * @param pageable 페이지네이션 정보
     * @return 알림 목록 Slice
     */
    Slice<Notification> findByUserAndTypeOrderByCreatedAtDesc(User user, NotificationType type, Pageable pageable);

    /**
     * 특정 사용자의 읽지 않은 알림 개수를 조회합니다.
     *
     * @param user 알림을 받을 사용자
     * @return 읽지 않은 알림 개수
     */
    Long countByUserAndIsReadFalse(User user);

    /**
     * 특정 사용자의 알림 ID 목록에 해당하는 알림들을 조회합니다.
     *
     * @param ids  알림 ID 리스트
     * @param user 알림을 받을 사용자
     * @return 알림 엔티티 목록
     */
    List<Notification> findByIdInAndUser(List<Long> ids, User user);
}
