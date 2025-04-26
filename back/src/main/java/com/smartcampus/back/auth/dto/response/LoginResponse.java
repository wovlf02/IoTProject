package com.smartcampus.back.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * LoginResponse
 * <p>
 * 로그인 성공 시 클라이언트에게 반환하는 응답 DTO입니다.
 * AccessToken, RefreshToken, 사용자 기본 정보를 포함합니다.
 * </p>
 */
@Getter
@Builder
public class LoginResponse {

    /**
     * Access Token (JWT)
     */
    private final String accessToken;

    /**
     * Refresh Token (JWT)
     */
    private final String refreshToken;

    /**
     * 로그인한 사용자 아이디 (username)
     */
    private final String username;

    /**
     * 로그인한 사용자 닉네임
     */
    private final String nickname;

    /**
     * 로그인한 사용자 이메일
     */
    private final String email;
}
