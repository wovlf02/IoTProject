package com.smartcampus.back.dto.chat.response;

import com.smartcampus.back.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 채팅방 요약 정보를 응답할 때 사용하는 DTO입니다.
 * 채팅방 리스트 조회 API에서 사용됩니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class ChatRoomSummaryResponse {

    private Long roomId;
    private String roomName;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private int unreadCount;

    /**
     * ChatRoom 객체로부터 ChatRoomSummaryResponse 변환
     *
     * @param chatRoom ChatRoom 엔티티
     * @param unreadCount 안읽은 메시지 수
     * @return ChatRoomSummaryResponse
     */
    public static ChatRoomSummaryResponse from(ChatRoom chatRoom, int unreadCount) {
        return ChatRoomSummaryResponse.builder()
                .roomId(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .lastMessage(chatRoom.getLastMessage())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .unreadCount(unreadCount)
                .build();
    }
}
