package com.smartcampus.back.chat.dto.response;

import com.smartcampus.back.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ChatRoomListResponse
 * <p>
 * 사용자가 참여 중인 채팅방 목록을 조회할 때 사용하는 응답 DTO입니다.
 * </p>
 */
@Getter
@Builder
public class ChatRoomListResponse {

    private final List<ChatRoomSummary> rooms;

    /**
     * ChatRoom 리스트를 ChatRoomListResponse로 변환하는 메서드
     *
     * @param chatRooms 채팅방 엔티티 리스트
     * @return 변환된 응답 객체
     */
    public static ChatRoomListResponse fromEntities(List<ChatRoom> chatRooms) {
        List<ChatRoomSummary> roomSummaries = chatRooms.stream()
                .map(ChatRoomSummary::fromEntity)
                .collect(Collectors.toList());

        return ChatRoomListResponse.builder()
                .rooms(roomSummaries)
                .build();
    }

    /**
     * 채팅방 요약 정보 DTO
     */
    @Getter
    @Builder
    public static class ChatRoomSummary {

        /**
         * 채팅방 ID
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
         * 채팅방 생성 시각
         */
        private final LocalDateTime createdAt;

        public static ChatRoomSummary fromEntity(ChatRoom chatRoom) {
            return ChatRoomSummary.builder()
                    .roomId(chatRoom.getId())
                    .name(chatRoom.getName())
                    .roomType(chatRoom.getRoomType())
                    .createdAt(chatRoom.getCreatedAt())
                    .build();
        }
    }
}
