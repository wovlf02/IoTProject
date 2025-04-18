package com.smartcampus.back.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 아이디 찾기 응답 DTO
 */
@Getter
@AllArgsConstructor
public class UsernameFindResponse {

    /**
     * 사용자의 아이디
     */
    private String username;
}
