package com.smartcampus.back.dto.chat.response;

import com.smartcampus.back.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 채팅 메시지 정보를 담는 응답 DTO입니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class ChatMessageResponse {

    /**
     * 메시지 ID
     */
    private Long messageId;

    /**
     * 채팅방 ID
     */
    private Long roomId;

    /**
     * 발신자 사용자 ID
     */
    private Long senderId;

    /**
     * 메시지 내용
     */
    private String message;

    /**
     * 메시지 타입 (TEXT / IMAGE / FILE)
     */
    private String type;

    /**
     * 파일 URL (이미지 또는 파일일 경우)
     */
    private String fileUrl;

    /**
     * 전송 시각
     */
    private LocalDateTime sentAt;

    /**
     * 수신자 기준 읽음 여부
     */
    private boolean isRead;

    /**
     * 엔티티 기반 응답 변환 메서드
     *
     * @param chatMessage ChatMessage 엔티티
     * @param isRead 현재 사용자의 메시지 읽음 여부
     * @return ChatMessageResponse
     */
    public static ChatMessageResponse from(ChatMessage chatMessage, boolean isRead) {
        return ChatMessageResponse.builder()
                .messageId(chatMessage.getId())
                .roomId(chatMessage.getChatRoom().getId())
                .senderId(chatMessage.getSender().getId())
                .message(chatMessage.getMessage())
                .type(chatMessage.getType())
                .fileUrl(chatMessage.getFileUrl())
                .sentAt(chatMessage.getSentAt())
                .isRead(isRead)
                .build();
    }
}
