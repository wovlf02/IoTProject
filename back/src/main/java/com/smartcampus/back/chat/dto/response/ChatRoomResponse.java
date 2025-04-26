package com.smartcampus.back.chat.dto.response;

import com.smartcampus.back.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ChatRoomResponse
 * <p>
 * 채팅방 상세 조회 시 사용하는 응답 DTO입니다.
 * 채팅방 메타 정보 (방 이름, 타입 등)를 제공합니다.
 * </p>
 */
@Getter
@Builder
public class ChatRoomResponse {

    /**
     * 채팅방 고유 ID
     */
    private final Long roomId;

    /**
     * 채팅방 이름
     */
    private final String name;

    /**
     * 채팅방 타입 (DIRECT, GROUP)
     */
    private final ChatRoom.RoomType roomType;

    /**
     * 채팅방 생성 일시
     */
    private final LocalDateTime createdAt;

    /**
     * ChatRoom 엔티티를 ChatRoomResponse로 변환하는 메서드
     *
     * @param chatRoom 채팅방 엔티티
     * @return 변환된 응답 객체
     */
    public static ChatRoomResponse fromEntity(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .name(chatRoom.getName())
                .roomType(chatRoom.getRoomType())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
