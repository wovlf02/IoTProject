package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 이메일 인증 코드 발송 요청 DTO
 * <p>
 * 회원가입 과정에서 이메일 인증 코드를 발송할 때 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class EmailSendRequest {

    /**
     * 인증 코드를 받을 이메일 주소
     */
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;
}
