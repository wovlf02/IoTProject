package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 비밀번호 재설정 응답 DTO
 */
@Getter
@AllArgsConstructor
public class ResetPasswordResponse {

    /**
     * 결과 메시지
     */
    private String message;
}
