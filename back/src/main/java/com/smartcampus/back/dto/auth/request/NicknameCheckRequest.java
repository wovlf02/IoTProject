package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 닉네임 중복 확인 요청 DTO입니다.
 * 사용자가 입력한 닉네임이 사용 가능한지 확인합니다.
 */
@Getter
@NoArgsConstructor
public class NicknameCheckRequest {

    /**
     * 중복 확인할 닉네임
     */
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    /**
     * 생성자
     *
     * @param nickname 확인할 닉네임
     */
    public NicknameCheckRequest(String nickname) {
        this.nickname = nickname;
    }
}
