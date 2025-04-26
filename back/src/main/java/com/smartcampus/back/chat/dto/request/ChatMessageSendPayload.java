package com.smartcampus.back.chat.dto.request;

import com.smartcampus.back.chat.entity.ChatMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatMessageSendPayload
 * <p>
 * WebSocket을 통해 채팅방에 메시지를 전송할 때 사용하는 요청 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class ChatMessageSendPayload {

    /**
     * 채팅방 ID
     */
    @NotNull(message = "채팅방 ID는 필수입니다.")
    private Long roomId;

    /**
     * 메시지 타입 (TEXT, FILE)
     */
    @NotNull(message = "메시지 타입은 필수입니다.")
    private ChatMessage.MessageType messageType;

    /**
     * 메시지 내용 (TEXT 타입일 경우)
     */
    private String content;

    /**
     * 첨부파일 ID (FILE 타입일 경우)
     */
    private Long attachmentId;
}
