package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 중복 확인 요청 DTO입니다.
 * 사용자가 입력한 이메일 주소가 사용 가능한지 확인합니다.
 */
@Getter
@NoArgsConstructor
public class EmailCheckRequest {

    /**
     * 중복 확인할 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 생성자
     *
     * @param email 확인할 이메일
     */
    public EmailCheckRequest(String email) {
        this.email = email;
    }
}
