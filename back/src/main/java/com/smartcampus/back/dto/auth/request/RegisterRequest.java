package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    /**
     * 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    /**
     * 사용자 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    /**
     * 사용자 이메일
     */
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 사용자 닉네임
     */
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    /**
     * 프로필 이미지 URL (선택)
     */
    private String profileImageUrl;

    /**
     * 사용자가 선택한 학교 ID
     */
    @NotNull(message = "학교를 선택해야 합니다.")
    private Long universityId;
}