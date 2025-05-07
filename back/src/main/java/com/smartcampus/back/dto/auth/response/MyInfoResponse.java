package com.smartcampus.back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 로그인된 사용자 본인의 정보를 응답하는 DTO입니다.
 * 사용자 기본 정보 및 상태, 가입 및 마지막 로그인 일시를 포함합니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class MyInfoResponse {

    /**
     * 사용자 고유 ID
     */
    private Long id;

    /**
     * 사용자 아이디
     */
    private String username;

    /**
     * 사용자 닉네임
     */
    private String nickname;

    /**
     * 이메일 주소
     */
    private String email;

    /**
     * 프로필 이미지 URL
     */
    private String profileImageUrl;

    /**
     * 사용자 권한 (예: USER, ADMIN)
     */
    private String role;

    /**
     * 계정 상태 (예: ACTIVE, BANNED)
     */
    private String status;

    /**
     * 가입 일시
     */
    private LocalDateTime registeredAt;

    /**
     * 마지막 로그인 일시
     */
    private LocalDateTime lastLoginAt;
}
