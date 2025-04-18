package com.smartcampus.back.community.friend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 닉네임 기반 검색 결과 응답 DTO
 */
@Getter
@Builder
public class FriendSearchResponse {

    @Schema(description = "사용자 ID", example = "10")
    private Long userId;

    @Schema(description = "사용자 닉네임", example = "헬로캠퍼스")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://smartcampus.com/profiles/10.png")
    private String profileImageUrl;
}
