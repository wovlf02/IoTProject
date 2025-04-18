package com.smartcampus.back.common.security;

/**
 * Spring Security 및 JWT 인증 처리에 사용되는 상수 정의 클래스입니다.
 */
public final class SecurityConstants {

    private SecurityConstants() {
        // 인스턴스화 방지
    }

    /**
     * Authorization 헤더 키
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * Bearer 토큰 접두어
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * JWT 토큰 타입
     */
    public static final String TOKEN_TYPE = "JWT";

    /**
     * 토큰 Claim 키 (사용자 식별자)
     */
    public static final String CLAIM_USER_ID = "userId";

    /**
     * Access Token 만료 시간 (단위: ms)
     * 실제 값은 application.yml에서 설정
     */
    public static final long DEFAULT_ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60; // 1시간

    /**
     * Refresh Token 만료 시간 (단위: ms)
     */
    public static final long DEFAULT_REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7일
}
