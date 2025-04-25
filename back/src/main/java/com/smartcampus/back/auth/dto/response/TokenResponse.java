package com.smartcampus.back.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 토큰 재발급 응답 DTO
 * <p>
 * 사용자가 refreshToken을 이용해 accessToken 재발급을 요청했을 때,
 * 새로 발급된 accessToken을 반환합니다.
 * </p>
 */
@Getter
@Builder
public class TokenResponse {

    /**
     * 새로 발급된 Access Token (JWT)
     */
    private String accessToken;
}
