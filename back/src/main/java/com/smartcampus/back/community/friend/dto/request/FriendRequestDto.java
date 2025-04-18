package com.smartcampus.back.community.friend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 친구 요청 전송 시 사용되는 DTO
 */
@Getter
@Setter
public class FriendRequestDto {

    @Schema(description = "친구 요청 대상 사용자 ID", example = "42")
    @NotNull(message = "상대 사용자 ID는 필수입니다.")
    private Long targetUserId;
}
