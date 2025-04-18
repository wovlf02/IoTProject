package com.smartcampus.back.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1:1 채팅방 시작 요청 DTO
 *
 * 친구 또는 특정 사용자와의 직접 채팅 시작을 위한 요청입니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class DirectChatRequest {

    @Schema(description = "1:1 채팅 상대 사용자 ID", example = "73", required = true)
    @NotNull(message = "상대 userId는 필수입니다.")
    private Long userId;
}
