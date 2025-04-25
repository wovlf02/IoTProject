package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 중복 확인 요청 DTO
 * <p>
 * 회원가입 시, 사용자가 입력한 아이디의 중복 여부를 서버에 요청할 때 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class UsernameCheckRequest {

    /**
     * 중복 확인할 아이디 (username)
     */
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 30, message = "아이디는 4자 이상 30자 이하로 입력해주세요.")
    private String username;
}
