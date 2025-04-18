package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 닉네임 중복 확인 요청 DTO
 */
@Getter
@Setter
public class NicknameCheckRequest {

    /**
     * 중복 확인할 닉네임
     */
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}
