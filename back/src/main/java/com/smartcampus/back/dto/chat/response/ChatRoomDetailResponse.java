package com.smartcampus.back.dto.chat.response;

import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 채팅방 상세 정보를 응답할 때 사용하는 DTO입니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class ChatRoomDetailResponse {

    /** 채팅방 ID */
    private Long roomId;

    /** 채팅방 이름 */
    private String roomName;

    /** 그룹 여부 (true: 그룹채팅, false: 1:1) */
    private boolean isGroup;

    /** 참여자 목록 */
    private List<ChatMemberResponse> members;

    /**
     * ChatRoom + ChatRoomMember 리스트를 기반으로 상세 응답 객체 생성
     *
     * @param chatRoom 채팅방
     * @param members 채팅방 멤버들
     * @return ChatRoomDetailResponse 인스턴스
     */
    public static ChatRoomDetailResponse of(ChatRoom chatRoom, List<ChatRoomMember> members) {
        return ChatRoomDetailResponse.builder()
                .roomId(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .isGroup(chatRoom.getIsGroup())
                .members(members.stream()
                        .map(ChatMemberResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
