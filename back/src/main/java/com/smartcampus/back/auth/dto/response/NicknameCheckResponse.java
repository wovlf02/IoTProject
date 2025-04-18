package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 닉네임 중복 확인 응답 DTO
 */
@Getter
@AllArgsConstructor
public class NicknameCheckResponse {

    /**
     * 중복 여부 (true: 사용 가능)
     */
    private boolean available;
}
