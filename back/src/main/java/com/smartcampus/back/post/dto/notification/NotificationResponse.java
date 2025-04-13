package com.smartcampus.back.post.dto.notification;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 클라이언트에게 전달될 알림 응답 DTO입니다.
 * 알림 제목, 내용, 읽음 상태, 생성 시각 정보를 포함합니다.
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
     * 알림 본문
     */
    private String body;

    /**
     * 읽음 여부
     */
    private boolean read;

    /**
     * 알림 생성 시각
     */
    private LocalDateTime createdAt;
}
