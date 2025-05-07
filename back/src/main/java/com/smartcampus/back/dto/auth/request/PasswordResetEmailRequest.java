package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 재설정을 위한 인증 코드 발송 요청 DTO입니다.
 * 사용자가 입력한 이메일 주소로 인증 코드를 전송합니다.
 */
@Getter
@NoArgsConstructor
public class PasswordResetEmailRequest {

    /**
     * 인증 코드를 발송할 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 생성자
     *
     * @param email 이메일 주소
     */
    public PasswordResetEmailRequest(String email) {
        this.email = email;
    }
}
