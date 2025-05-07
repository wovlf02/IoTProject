package com.smartcampus.back.dto.chat.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * WebSocket을 통해 서버에 전송되는 채팅 메시지 요청 DTO입니다.
 */
@Getter
@NoArgsConstructor
public class ChatMessageSendRequest {

    /**
     * 채팅방 ID
     */
    @NotNull(message = "roomId는 필수입니다.")
    private Long roomId;

    /**
     * 메시지 본문
     */
    @NotBlank(message = "메시지 내용은 비어 있을 수 없습니다.")
    private String message;

    /**
     * 메시지 타입 (TEXT / IMAGE / FILE)
     */
    @NotBlank(message = "메시지 타입은 필수입니다.")
    private String type;

    /**
     * 파일 또는 이미지 URL (선택)
     */
    private String fileUrl;
}
