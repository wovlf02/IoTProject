package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 재설정 요청 DTO
 * <p>
 * 아이디와 이메일을 입력받아 비밀번호 재설정을 위한 본인 확인을 수행합니다.
 * </p>
 */
@Getter
@Setter
public class PasswordResetRequest {

    /**
     * 본인 확인용 아이디
     */
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;

    /**
     * 본인 확인용 이메일
     */
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;
}
