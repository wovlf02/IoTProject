package com.smartcampus.back.dto.chat.response;

import com.smartcampus.back.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 채팅방 생성 결과 또는 단일 채팅방 정보를 응답할 때 사용하는 DTO입니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class ChatRoomResponse {

    /** 채팅방 ID */
    private Long roomId;

    /** 채팅방 이름 */
    private String roomName;

    /** 그룹 여부 (true: 그룹, false: 1:1) */
    private boolean isGroup;

    /** 생성 시각 */
    private LocalDateTime createdAt;

    /**
     * ChatRoom 엔티티로부터 ChatRoomResponse 생성
     *
     * @param chatRoom ChatRoom 엔티티
     * @return ChatRoomResponse DTO
     */
    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .isGroup(chatRoom.getIsGroup())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
