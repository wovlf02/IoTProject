package com.smartcampus.back.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 알림 상세 응답 DTO
 * <p>
 * 사용자가 받은 개별 알림 정보를 표현합니다.
 * </p>
 */
@Getter
@Builder
public class NotificationResponse {

    /**
     * 알림 ID
     */
    private Long id;

    /**
     * 알림 제목
     */
    private String title;

    /**
     * 알림 메시지 본문
     */
    private String body;

    /**
     * 알림 생성 시각
     */
    private LocalDateTime createdAt;

    /**
     * 읽음 여부
     */
    private boolean isRead;

    /**
     * 알림 유형 (예: FRIEND_REQUEST, MESSAGE, SYSTEM 등)
     */
    private String type;

    /**
     * 관련 리소스 ID (예: 게시글 ID, 채팅방 ID 등)
     */
    private Long referenceId;

    /**
     * 관련 리소스 URL (선택적)
     */
    private String referenceUrl;
}
