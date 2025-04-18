package com.smartcampus.back.community.friend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 친구 요청, 수락, 차단 등의 단순 응답 DTO
 */
@Getter
@Builder
public class FriendSimpleResponse {

    @Schema(description = "응답 메시지", example = "친구 요청이 전송되었습니다.")
    private String message;

    @Schema(description = "대상 사용자 ID", example = "15")
    private Long targetUserId;
}
