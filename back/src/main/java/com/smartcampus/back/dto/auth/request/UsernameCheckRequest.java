package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아이디 중복 확인 요청 DTO입니다.
 * 사용자가 입력한 아이디가 중복되지 않았는지 확인합니다.
 */
@Getter
@NoArgsConstructor
public class UsernameCheckRequest {

    /**
     * 중복 확인할 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    /**
     * 생성자
     *
     * @param username 사용자 아이디
     */
    public UsernameCheckRequest(String username) {
        this.username = username;
    }
}
