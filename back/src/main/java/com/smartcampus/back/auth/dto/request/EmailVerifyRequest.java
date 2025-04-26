package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EmailVerifyRequest
 * <p>
 * 이메일 인증코드를 검증할 때 사용하는 요청 DTO입니다.
 * (ex. 회원가입 인증, 아이디 찾기 인증, 비밀번호 재설정 인증 등)
 * </p>
 */
@Getter
@NoArgsConstructor
public class EmailVerifyRequest {

    /**
     * 인증할 이메일 주소
     */
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 입력한 인증 코드
     */
    @NotBlank(message = "인증코드를 입력해 주세요.")
    private String code;
}
