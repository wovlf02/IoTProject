package com.smartcampus.back.auth.dto.response;

import com.smartcampus.back.auth.entity.User;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 프로필 응답 DTO
 * <p>
 * 로그인된 사용자 또는 검색된 사용자의 정보를 응답할 때 사용됩니다.
 * </p>
 */
@Getter
@Builder
public class UserProfileResponse {

    /**
     * 사용자 아이디 (username)
     */
    private String username;

    /**
     * 사용자 닉네임
     */
    private String nickname;

    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 프로필 이미지 URL (nullable)
     */
    private String profileImageUrl;

    /**
     * 계정 상태 (ACTIVE, SUSPENDED, WITHDRAWN 등)
     */
    private String status;

    /**
     * User 엔티티로부터 DTO 변환
     *
     * @param user User 엔티티
     * @return UserProfileResponse DTO
     */
    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .status(user.getStatus().name())
                .build();
    }
}
