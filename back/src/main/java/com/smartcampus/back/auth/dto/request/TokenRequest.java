package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 토큰 요청 DTO
 * <p>
 * 로그아웃 또는 토큰 재발급 요청 시, 사용자의 Refresh Token을 전달합니다.
 * </p>
 */
@Getter
@Setter
public class TokenRequest {

    /**
     * 사용자의 Refresh Token
     */
    @NotBlank(message = "Refresh Token은 필수 입력값입니다.")
    private String refreshToken;
}
