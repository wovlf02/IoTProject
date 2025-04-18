package com.smartcampus.back.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클라이언트가 서버로 전송하는 채팅 메시지 요청 DTO
 *
 * WebSocket STOMP 경로: /pub/chat/message
 */
@Getter
@Setter
@NoArgsConstructor
public class ChatMessageRequest {

    @Schema(description = "채팅방 ID", example = "1001", required = true)
    @NotNull(message = "roomId는 필수입니다.")
    private Long roomId;

    @Schema(description = "메시지 전송자(작성자) ID", example = "42", required = true)
    @NotNull(message = "senderId는 필수입니다.")
    private Long senderId;

    @Schema(description = "메시지 텍스트", example = "안녕하세요!")
    private String message;

    @Schema(description = "파일 첨부 여부", example = "false")
    private boolean hasFile;

    @Schema(description = "파일명 (첨부 시)", example = "chat_image.png")
    private String fileName;

    @Schema(description = "파일 MIME 타입", example = "image/png")
    private String contentType;
}
