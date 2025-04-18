package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 이메일 인증 요청 DTO
 *
 * 코드 발송 요청 또는 코드 검증에 모두 사용됨
 */
@Getter
@Setter
public class EmailVerificationRequest {

    /**
     * 대상 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 인증 코드
     */
    private String code;
}
