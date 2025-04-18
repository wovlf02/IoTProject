package com.smartcampus.back.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 채팅방 입장 또는 퇴장 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ChatJoinRequest {

    @Schema(description = "채팅에 입장 또는 퇴장할 사용자 ID", example = "42", required = true)
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
}
