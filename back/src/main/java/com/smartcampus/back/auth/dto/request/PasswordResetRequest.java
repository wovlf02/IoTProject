package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PasswordResetRequest
 * <p>
 * 비밀번호 재설정(초기화) 요청 시 사용하는 DTO입니다.
 * (아이디 + 이메일을 함께 입력받아 본인 인증을 수행합니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class PasswordResetRequest {

    /**
     * 사용자 로그인 아이디 (username)
     */
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;

    /**
     * 사용자 이메일 주소
     */
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;
}
