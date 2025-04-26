package com.smartcampus.back.notification.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.global.util.SecurityUtil;
import com.smartcampus.back.notification.dto.request.NotificationDeleteRequest;
import com.smartcampus.back.notification.dto.request.NotificationReadRequest;
import com.smartcampus.back.notification.dto.response.NotificationDetailResponse;
import com.smartcampus.back.notification.dto.response.NotificationResponse;
import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import com.smartcampus.back.notification.exception.NotificationNotFoundException;
import com.smartcampus.back.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * NotificationService
 * <p>
 * 알림 조회, 삭제, 읽음 처리 등의 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * 로그인 사용자의 알림 목록을 조회합니다. (Cursor 기반 무한 스크롤, 타입 필터링 지원)
     *
     * @param cursor 마지막으로 조회한 알림 ID
     * @param size 페이지 크기
     * @param type 알림 타입 필터 (nullable)
     * @return 알림 목록 Slice
     */
    public Slice<NotificationResponse> getNotifications(Long cursor, int size, NotificationType type) {
        User user = SecurityUtil.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(0, size);

        Slice<Notification> notifications;
        if (type == null) {
            notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user, pageRequest);
        } else {
            notifications = notificationRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type, pageRequest);
        }

        return notifications.map(NotificationResponse::fromEntity);
    }

    /**
     * 알림 단건 상세 정보를 조회합니다.
     *
     * @param notificationId 알림 ID
     * @return 알림 상세 응답
     */
    public NotificationDetailResponse getNotificationDetail(Long notificationId) {
        Notification notification = findNotification(notificationId);
        return NotificationDetailResponse.fromEntity(notification);
    }

    /**
     * 알림 단건 삭제
     *
     * @param notificationId 알림 ID
     */
    @Transactional
    public void deleteNotification(Long notificationId) {
        Notification notification = findNotification(notificationId);
        notificationRepository.delete(notification);
    }

    /**
     * 알림 여러 개 삭제
     *
     * @param request 삭제할 알림 ID 리스트 요청 객체
     */
    @Transactional
    public void deleteNotifications(NotificationDeleteRequest request) {
        User user = SecurityUtil.getCurrentUser();
        List<Notification> notifications = notificationRepository.findByIdInAndUser(request.getNotificationIds(), user);

        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("삭제할 알림을 찾을 수 없습니다.");
        }

        notificationRepository.deleteAll(notifications);
    }

    /**
     * 알림 단건 읽음 처리
     *
     * @param notificationId 알림 ID
     */
    @Transactional
    public void readNotification(Long notificationId) {
        Notification notification = findNotification(notificationId);
        notification.setIsRead(true);
    }

    /**
     * 알림 여러 개 읽음 처리
     *
     * @param request 읽음 처리할 알림 ID 리스트 요청 객체
     */
    @Transactional
    public void readNotifications(NotificationReadRequest request) {
        User user = SecurityUtil.getCurrentUser();
        List<Notification> notifications = notificationRepository.findByIdInAndUser(request.getNotificationIds(), user);

        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("읽음 처리할 알림을 찾을 수 없습니다.");
        }

        for (Notification notification : notifications) {
            notification.setIsRead(true);
        }
    }

    /**
     * 전체 알림 읽음 처리
     */
    @Transactional
    public void readAllNotifications() {
        User user = SecurityUtil.getCurrentUser();
        Slice<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user, PageRequest.of(0, Integer.MAX_VALUE));

        for (Notification notification : notifications) {
            notification.setIsRead(true);
        }
    }

    /**
     * 읽지 않은 알림 개수 조회
     *
     * @return 읽지 않은 알림 수
     */
    public Long getUnreadNotificationCount() {
        User user = SecurityUtil.getCurrentUser();
        return notificationRepository.countByUserAndIsReadFalse(user);
    }

    /**
     * 알림 ID로 알림을 조회합니다.
     * 존재하지 않을 경우 예외를 발생시킵니다.
     *
     * @param notificationId 알림 ID
     * @return 알림 엔티티
     */
    private Notification findNotification(Long notificationId) {
        User user = SecurityUtil.getCurrentUser();
        return notificationRepository.findById(notificationId)
                .filter(notification -> notification.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new NotificationNotFoundException("해당 알림을 찾을 수 없습니다."));
    }
}
