package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 재설정 요청 DTO입니다.
 * 이메일 인증 후 새로운 비밀번호를 설정할 때 사용합니다.
 */
@Getter
@NoArgsConstructor
public class PasswordResetRequest {

    /**
     * 사용자 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 이메일 인증 코드
     */
    @NotBlank(message = "인증 코드는 필수입니다.")
    private String code;

    /**
     * 새로 설정할 비밀번호
     */
    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상 100자 이하로 입력해주세요.")
    private String newPassword;

    /**
     * 생성자
     *
     * @param email 사용자 이메일
     * @param code 인증 코드
     * @param newPassword 새 비밀번호
     */
    public PasswordResetRequest(String email, String code, String newPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
    }
}
