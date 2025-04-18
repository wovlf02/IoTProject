package com.smartcampus.back.community.friend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 친구 요청 목록 응답 DTO
 */
@Getter
@Builder
public class FriendRequestListResponse {

    @Schema(description = "받은 친구 요청 목록")
    private List<FriendSearchResponse> requests;
}
