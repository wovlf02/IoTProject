package com.smartcampus.back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공 시 반환되는 응답 DTO입니다.
 * JWT 액세스 토큰과 리프레시 토큰을 포함합니다.
 */
@Getter
@AllArgsConstructor
public class LoginResponse {

    /**
     * 액세스 토큰 (Authorization: Bearer {accessToken})
     */
    private String accessToken;

    /**
     * 리프레시 토큰 (재발급 요청 시 사용)
     */
    private String refreshToken;
}
