package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 회원가입 요청 DTO입니다.
 * 필수 정보(아이디, 닉네임, 비밀번호, 이메일)와
 * 선택 정보(시간표 교시 목록)를 함께 전달합니다.
 */
@Getter
@NoArgsConstructor
public class SignupRequest {

    /**
     * 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 30, message = "아이디는 4자 이상 30자 이하로 입력해주세요.")
    private String username;

    /**
     * 사용자 닉네임
     */
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하로 입력해주세요.")
    private String nickname;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상 100자 이하로 입력해주세요.")
    private String password;

    /**
     * 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 시간표 교시 정보 목록 (선택)
     */
    private List<TimetableSlotDto> timetable;
}
