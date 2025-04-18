package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청 DTO
 *
 * multipart/form-data 요청으로 프로필 이미지 포함 가능
 */
@Getter
@Setter
public class RegisterRequest {

    /**
     * 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;

    /**
     * 이메일
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 닉네임
     */
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}
