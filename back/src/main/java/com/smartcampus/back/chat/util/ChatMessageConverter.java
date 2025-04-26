package com.smartcampus.back.chat.util;

import com.smartcampus.back.chat.dto.response.WebSocketMessageResponse;
import com.smartcampus.back.chat.entity.ChatMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ChatMessageConverter
 * <p>
 * ChatMessage 엔티티를 WebSocketMessageResponse로 변환하는 유틸리티 클래스입니다.
 * WebSocket 통신 시 일관된 메시지 포맷 제공을 위해 사용합니다.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageConverter {

    /**
     * ChatMessage 엔티티를 WebSocketMessageResponse로 변환
     *
     * @param message ChatMessage 엔티티
     * @return 변환된 WebSocketMessageResponse
     */
    public static WebSocketMessageResponse toWebSocketMessage(ChatMessage message) {
        return WebSocketMessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .senderNickname(message.getSender().getNickname())
                .messageType(message.getMessageType())
                .content(message.isDeleted() ? "삭제된 메시지입니다." : message.getContent())
                .attachmentId(message.getAttachmentId())
                .sentAt(message.getSentAt())
                .deleted(message.isDeleted())
                .build();
    }
}
