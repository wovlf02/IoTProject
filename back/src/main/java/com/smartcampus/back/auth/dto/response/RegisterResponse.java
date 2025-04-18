package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원가입 응답 DTO
 */
@Getter
@AllArgsConstructor
public class RegisterResponse {

    /**
     * 사용자 고유 ID
     */
    private Long userId;

    /**
     * 응답 메시지
     */
    private String message;
}
