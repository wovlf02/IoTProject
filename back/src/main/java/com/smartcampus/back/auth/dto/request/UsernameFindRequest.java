package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 찾기 요청 DTO
 */
@Getter
@Setter
public class UsernameFindRequest {

    /**
     * 회원가입 시 사용한 이메일
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;
}
