package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LoginRequest
 * <p>
 * 로그인 요청 시 사용하는 DTO입니다.
 * (ID와 비밀번호를 입력받습니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class LoginRequest {

    /**
     * 로그인 아이디 (username)
     */
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;

    /**
     * 로그인 비밀번호
     */
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}
