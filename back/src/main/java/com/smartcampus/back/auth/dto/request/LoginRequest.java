package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 DTO
 *
 * 아이디와 비밀번호를 전달받아 인증 처리를 수행
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
