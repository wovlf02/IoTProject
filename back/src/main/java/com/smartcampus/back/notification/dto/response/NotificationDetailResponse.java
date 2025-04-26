package com.smartcampus.back.notification.dto.response;

import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * NotificationDetailResponse
 * <p>
 * 알림 단건 상세 조회 응답 객체입니다.
 * 알림 제목, 내용, 타입, 읽음 여부, 생성 일시를 제공합니다.
 * </p>
 */
@Getter
@Builder
public class NotificationDetailResponse {

    /**
     * 알림 ID
     */
    private Long notificationId;

    /**
     * 알림 제목
     */
    private String title;

    /**
     * 알림 본문
     */
    private String body;

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
     * Notification 엔티티를 NotificationDetailResponse로 변환합니다.
     *
     * @param notification 알림 엔티티
     * @return 변환된 NotificationDetailResponse
     */
    public static NotificationDetailResponse fromEntity(Notification notification) {
        return NotificationDetailResponse.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .body(notification.getBody())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
