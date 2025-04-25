package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 이메일 입력 요청 DTO
 * <p>
 * 아이디 찾기, 비밀번호 재설정 본인확인 등에서 이메일 입력용으로 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class EmailRequest {

    /**
     * 사용자 이메일
     */
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식을 입력해주세요.")
    private String email;
}
