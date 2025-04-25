package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 닉네임 중복 확인 요청 DTO
 * <p>
 * 회원가입 또는 닉네임 변경 시 닉네임 중복 여부를 검증합니다.
 * </p>
 */
@Getter
@Setter
public class NicknameCheckRequest {

    /**
     * 중복 확인할 닉네임
     */
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}
