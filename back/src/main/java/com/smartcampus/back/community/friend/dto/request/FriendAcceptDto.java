package com.smartcampus.back.community.friend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 친구 요청 수락/거절 요청 DTO
 */
@Getter
@Setter
public class FriendAcceptDto {

    @Schema(description = "친구 요청 ID", example = "123")
    @NotNull(message = "요청 ID는 필수입니다.")
    private Long requestId;

    @Schema(description = "수락 여부 (true = 수락, false = 거절)", example = "true")
    @NotNull(message = "수락 여부는 필수입니다.")
    private Boolean accepted;
}
