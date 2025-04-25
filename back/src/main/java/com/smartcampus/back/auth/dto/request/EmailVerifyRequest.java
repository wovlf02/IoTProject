package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 이메일 인증 코드 검증 요청 DTO
 * <p>
 * 사용자가 이메일로 받은 인증 코드를 입력해 서버에 검증 요청할 때 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class EmailVerifyRequest {

    /**
     * 인증할 이메일 주소
     */
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 인증 코드 (6자리 숫자)
     */
    @NotBlank(message = "인증 코드는 필수 입력값입니다.")
    private String code;
}
