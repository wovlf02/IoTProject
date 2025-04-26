package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EmailRequest
 * <p>
 * 이메일을 단독으로 입력받을 때 사용하는 요청 DTO입니다.
 * (ex. 아이디 찾기 - 이메일 입력)
 * </p>
 */
@Getter
@NoArgsConstructor
public class EmailRequest {

    /**
     * 사용자 이메일 주소
     */
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;
}
