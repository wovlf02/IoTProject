package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TokenRequest
 * <p>
 * 토큰 기반 요청 (로그아웃, 토큰 재발급 등) 시 사용하는 DTO입니다.
 * 주로 refreshToken을 전달합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class TokenRequest {

    /**
     * Refresh Token
     */
    @NotBlank(message = "Refresh Token을 입력해 주세요.")
    private String refreshToken;
}
