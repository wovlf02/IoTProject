package com.smartcampus.back.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 응답 DTO
 * <p>
 * 로그인 성공 시 클라이언트에 전달할 사용자 인증/프로필 정보와 JWT 토큰 정보를 담습니다.
 * </p>
 */
@Getter
@Builder
public class LoginResponse {

    /**
     * Access Token (JWT, 인증용)
     */
    private String accessToken;

    /**
     * Refresh Token (재발급용)
     */
    private String refreshToken;

    /**
     * 사용자 아이디 (username)
     */
    private String username;

    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 사용자 닉네임
     */
    private String nickname;
}
