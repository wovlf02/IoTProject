package com.smartcampus.back.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅 메시지 응답 DTO
 *
 * WebSocket 또는 메시지 히스토리 조회 시 사용됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    @Schema(description = "채팅 메시지 ID", example = "9876")
    private Long messageId;

    @Schema(description = "메시지를 보낸 사용자 ID", example = "42")
    private Long senderId;

    @Schema(description = "채팅방 ID", example = "1001")
    private Long roomId;

    @Schema(description = "메시지 본문", example = "안녕하세요!")
    private String message;

    @Schema(description = "보낸 시간 (yyyy-MM-dd'T'HH:mm:ss)", example = "2025-04-18T14:23:45")
    private LocalDateTime sentAt;

    @Schema(description = "첨부파일 여부", example = "false")
    private boolean hasFile;

    @Schema(description = "파일명 (첨부된 경우)", example = "document.pdf")
    private String fileName;

    @Schema(description = "파일 미리보기 URL", example = "/api/chat/messages/files/123")
    private String previewUrl;

    @Schema(description = "파일 다운로드 URL", example = "/api/chat/messages/9876/download")
    private String downloadUrl;
}
