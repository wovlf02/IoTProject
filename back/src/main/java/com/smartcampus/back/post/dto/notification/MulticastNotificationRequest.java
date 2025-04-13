package com.smartcampus.back.post.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * FCM 다중 사용자 푸시 알림 요청 DTO
 */
@Getter
@Setter
public class MulticastNotificationRequest {

    /**
     * 최대 500개의 FCM 토큰 리스트
     */
    @NotEmpty(message = "최소 1개 이상의 토큰이 필요합니다.")
    @Size(max = 500, message = "최대 500개의 토큰만 지원됩니다.")
    private List<@NotBlank String> targetTokens;

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
