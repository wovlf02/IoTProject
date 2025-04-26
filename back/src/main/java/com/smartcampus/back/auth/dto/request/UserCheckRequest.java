package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserCheckRequest
 * <p>
 * 사용자 정보를 기준으로 조회하거나 검증할 때 사용하는 요청 DTO입니다.
 * (username, email, nickname 등 다양한 필드에 재사용할 수 있습니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class UserCheckRequest {

    /**
     * 사용자 검증용 입력값
     * (username, email, nickname 중 하나를 전달)
     */
    @NotBlank(message = "필수 입력값을 입력해 주세요.")
    private String value;
}
