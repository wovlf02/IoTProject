package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 인증 코드 검증 응답 DTO
 */
@Getter
@AllArgsConstructor
public class VerifyEmailResponse {


    /**
     * 인증 성공 여부
     */
    private boolean verified;
}
