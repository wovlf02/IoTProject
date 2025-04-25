package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * SmartCampus 회원가입 요청 DTO
 * <p>
 * 사용자 기본 정보(아이디, 비밀번호, 닉네임, 이메일, 프로필 이미지)를 입력받아
 * 회원가입을 완료할 때 사용합니다.
 * </p>
 */
@Getter
@Setter
public class RegisterRequest {

    /**
     * 회원가입할 사용자 아이디 (username)
     */
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 30, message = "아이디는 4자 이상 30자 이하로 입력해주세요.")
    private String username;

    /**
     * 로그인할 때 사용할 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    /**
     * 사용자 닉네임
     */
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(max = 20, message = "닉네임은 20자 이하로 입력해주세요.")
    private String nickname;

    /**
     * 사용자 이메일 주소
     */
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 프로필 이미지 URL (nullable)
     */
    private String profileImageUrl;
}
