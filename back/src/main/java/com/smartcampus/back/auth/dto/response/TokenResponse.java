package com.smartcampus.back.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * TokenResponse
 * <p>
 * 토큰 재발급 성공 시 클라이언트에게 반환하는 응답 DTO입니다.
 * 새로 발급된 AccessToken을 전달합니다.
 * </p>
 */
@Getter
@Builder
public class TokenResponse {

    /**
     * 새로 발급된 Access Token
     */
    private final String accessToken;
}
