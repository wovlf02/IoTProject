package com.smartcampus.back.notification.dto.response;

import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * NotificationResponse
 * <p>
 * 알림 목록 조회 시 사용되는 응답 객체입니다.
 * 알림 제목, 타입, 읽음 여부, 생성 일시를 요약 제공합니다.
 * </p>
 */
@Getter
@Builder
public class NotificationResponse {

    /**
     * 알림 ID
     */
    private Long notificationId;

    /**
     * 알림 제목
     */
    private String title;

    /**
     * 알림 타입
     */
    private NotificationType type;

    /**
     * 읽음 여부
     */
    private Boolean isRead;

    /**
     * 알림 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * Notification 엔티티를 NotificationResponse로 변환합니다.
     *
     * @param notification 알림 엔티티
     * @return 변환된 NotificationResponse
     */
    public static NotificationResponse fromEntity(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
