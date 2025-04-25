package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 변경 요청 DTO
 * <p>
 * 로그인한 사용자가 현재 비밀번호를 검증 후, 새 비밀번호로 변경할 때 사용됩니다.
 * </p>
 */
@Getter
@Setter
public class PasswordChangeRequest {

    /**
     * 현재 비밀번호
     */
    @NotBlank(message = "현재 비밀번호는 필수 입력값입니다.")
    private String currentPassword;

    /**
     * 새 비밀번호
     */
    @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String newPassword;
}
