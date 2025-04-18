package com.smartcampus.back.community.friend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 차단된 친구 목록 응답 DTO
 */
@Getter
@Builder
public class BlockedFriendListResponse {

    @Schema(description = "차단된 친구 목록")
    private List<FriendSearchResponse> blockedFriends;
}
