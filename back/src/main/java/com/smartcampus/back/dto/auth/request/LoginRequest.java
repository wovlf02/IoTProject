package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청 DTO입니다.
 * 사용자 아이디와 비밀번호를 입력받아 로그인 인증을 수행합니다.
 */
@Getter
@NoArgsConstructor
public class LoginRequest {

    /**
     * 로그인할 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    /**
     * 로그인할 사용자 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    /**
     * 생성자
     *
     * @param username 사용자 아이디
     * @param password 사용자 비밀번호
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
