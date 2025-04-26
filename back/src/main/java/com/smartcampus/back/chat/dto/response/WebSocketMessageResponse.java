package com.smartcampus.back.chat.dto.response;

import com.smartcampus.back.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * WebSocketMessageResponse
 * <p>
 * WebSocket을 통해 실시간으로 수신하는 채팅 메시지 응답 DTO입니다.
 * (텍스트/파일 구분 및 삭제 메시지 표시 지원)
 * </p>
 */
@Getter
@Builder
public class WebSocketMessageResponse {

    /**
     * 메시지 고유 ID
     */
    private final Long id;

    /**
     * 보낸 사용자 ID
     */
    private final Long senderId;

    /**
     * 보낸 사용자 닉네임
     */
    private final String senderNickname;

    /**
     * 메시지 타입 (TEXT, FILE)
     */
    private final ChatMessage.MessageType messageType;

    /**
     * 텍스트 메시지 내용 (TEXT 타입일 경우)
     */
    private final String content;

    /**
     * 첨부파일 ID (FILE 타입일 경우)
     */
    private final Long attachmentId;

    /**
     * 메시지 발송 시간
     */
    private final LocalDateTime sentAt;

    /**
     * 삭제된 메시지 여부
     */
    private final boolean deleted;

    /**
     * ChatMessage 엔티티를 WebSocketMessageResponse로 변환하는 메서드
     *
     * @param message ChatMessage 엔티티
     * @return 변환된 응답 객체
     */
    public static WebSocketMessageResponse fromEntity(ChatMessage message) {
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
