package com.smartcampus.back.auth.dto.response;

import com.smartcampus.back.auth.entity.User;
import lombok.Builder;
import lombok.Getter;

/**
 * UserProfileResponse
 * <p>
 * 사용자 프로필 조회 시 반환하는 응답 DTO입니다.
 * username, nickname, email, profileImageUrl 정보를 제공합니다.
 * </p>
 */
@Getter
@Builder
public class UserProfileResponse {

    /**
     * 사용자 로그인 아이디
     */
    private final String username;

    /**
     * 사용자 닉네임
     */
    private final String nickname;

    /**
     * 사용자 이메일 주소
     */
    private final String email;

    /**
     * 프로필 이미지 URL
     */
    private final String profileImageUrl;

    /**
     * User 엔티티를 UserProfileResponse로 변환하는 메서드
     *
     * @param user User 엔티티
     * @return 변환된 UserProfileResponse
     */
    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
