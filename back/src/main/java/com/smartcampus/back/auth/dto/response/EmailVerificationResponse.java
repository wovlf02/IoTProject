package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 인증 코드 발송 응답 DTO
 */
@Getter
@AllArgsConstructor
public class EmailVerificationResponse {

    /**
     * 전송 결과 메시지
     */
    private String message;
}
