package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest
 * <p>
 * 최종 회원가입 제출 시 사용하는 요청 DTO입니다.
 * 기본정보(username, password, nickname, email)와 프로필 이미지 URL을 입력받습니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class RegisterRequest {

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

    /**
     * 사용자 닉네임
     */
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    /**
     * 사용자 이메일 주소
     */
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 프로필 이미지 URL (nullable 허용)
     */
    private String profileImageUrl;
}
