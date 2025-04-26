package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UsernameCheckRequest
 * <p>
 * 아이디(username) 중복 확인 요청 시 사용하는 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class UsernameCheckRequest {

    /**
     * 중복 확인할 로그인 아이디
     */
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;
}
