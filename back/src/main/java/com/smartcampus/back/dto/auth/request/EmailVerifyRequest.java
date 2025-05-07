package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증 코드 검증 요청 DTO입니다.
 * 사용자가 입력한 인증 코드가 서버에 저장된 것과 일치하는지 검증할 때 사용합니다.
 */
@Getter
@NoArgsConstructor
public class EmailVerifyRequest {

    /**
     * 인증을 요청한 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 사용자 입력 인증 코드
     */
    @NotBlank(message = "인증 코드는 필수입니다.")
    private String code;

    /**
     * 생성자
     *
     * @param email 이메일 주소
     * @param code 인증 코드
     */
    public EmailVerifyRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
