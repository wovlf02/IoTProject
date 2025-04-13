package com.smartcampus.back.post.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * FCM 단일 사용자 푸시 알림 요청 DTO
 */
@Getter
@Setter
public class NotificationRequest {

    /**
     * 알림을 받을 디바이스의 FCM 토큰
     */
    @NotBlank(message = "FCM 토큰은 필수입니다.")
    private String targetToken;

    /**
     * 알림 제목
     */
    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    /**
     * 알림 본문 내용
     */
    @NotBlank(message = "알림 본문은 필수입니다.")
    private String body;
}
