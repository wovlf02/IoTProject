package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증 코드 발송 요청 DTO입니다.
 * 사용자가 입력한 이메일 주소를 기반으로 인증 코드를 전송합니다.
 */
@Getter
@NoArgsConstructor
public class EmailSendRequest {

    /**
     * 인증 코드를 발송할 대상 이메일 주소
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    /**
     * 생성자 (테스트용 또는 직렬화용)
     *
     * @param email 이메일 주소
     */
    public EmailSendRequest(String email) {
        this.email = email;
    }
}
