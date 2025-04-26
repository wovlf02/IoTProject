package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PasswordChangeRequest
 * <p>
 * 비밀번호 변경 요청 시 사용하는 DTO입니다.
 * (현재 비밀번호를 요구하지 않고 새 비밀번호만 입력받습니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    /**
     * 아이디
     */
    @NotBlank(message = "아이디를 입력해 주세요")
    private String username;

    /**
     * 새 비밀번호
     */
    @NotBlank(message = "새 비밀번호를 입력해 주세요.")
    private String newPassword;
}
