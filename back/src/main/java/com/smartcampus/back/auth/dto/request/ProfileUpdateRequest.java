package com.smartcampus.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ProfileUpdateRequest
 * <p>
 * 사용자 프로필(닉네임, 프로필 이미지) 수정 요청 시 사용하는 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class ProfileUpdateRequest {

    /**
     * 수정할 닉네임
     */
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    /**
     * 수정할 프로필 이미지 URL
     * (nullable 허용 → 기본 이미지로 설정 가능)
     */
    private String profileImageUrl;
}
