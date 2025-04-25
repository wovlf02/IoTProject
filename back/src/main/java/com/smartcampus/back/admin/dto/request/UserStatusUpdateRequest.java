package com.smartcampus.back.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 상태 변경 요청 DTO
 * <p>
 * 관리자에 의해 사용자의 계정 상태를 변경할 때 사용된다.
 * 예: ACTIVE → SUSPENDED
 * </p>
 */
@Getter
@Setter
public class UserStatusUpdateRequest {

    /**
     * 변경할 사용자 상태
     * 예: ACTIVE, SUSPENDED, WITHDRAWN 등
     */
    @NotBlank(message = "상태 값은 필수입니다.")
    private String status;
}
