package com.smartcampus.back.post.dto.notification;

import lombok.Getter;
import lombok.Setter;

/**
 * FCM 푸시 알림 전송 요청 DTO
 */
@Getter
@Setter
public class NotificationRequest {

    /**
     * 알림을 받을 사용자의 디바이스 FCM 토큰
     */
    private String targetToken;

    /**
     * 알림 제목
     */
    private String title;

    /**
     * 알림 본문 내용
     */
    private String body;
}
