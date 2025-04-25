package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 닉네임 변경 요청 DTO
 * <p>
 * 로그인 후 사용자가 자신의 닉네임을 수정할 때 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class NicknameUpdateRequest {

    /**
     * 새로 변경할 닉네임
     */
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}
