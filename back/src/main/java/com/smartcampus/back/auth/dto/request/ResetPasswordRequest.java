package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 재설정 요청 DTO
 */
@Getter
@Setter
public class ResetPasswordRequest {

    /**
     * 대상 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    /**
     * 가입 시 이메일
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 새 비밀번호
     */
    @NotBlank(message = "새 비밀번호는 필수입니다.")
    private String newPassword;
}
