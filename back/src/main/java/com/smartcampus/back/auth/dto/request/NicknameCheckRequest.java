package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * NicknameCheckRequest
 * <p>
 * 닉네임 중복 확인 요청 시 사용하는 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class NicknameCheckRequest {

    /**
     * 중복 확인할 닉네임
     */
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;
}
