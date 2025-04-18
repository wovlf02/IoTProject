package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 중복 확인 요청 DTO
 */
@Getter
@Setter
public class UsernameCheckRequest {

    /**
     * 중복 확인할 사용자 아이디
     */
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;
}
