package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 DTO
 * <p>
 * 사용자 아이디와 비밀번호를 입력받아 로그인 요청을 처리합니다.
 * </p>
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * 로그인할 사용자 아이디 (username)
     */
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;

    /**
     * 로그인할 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
