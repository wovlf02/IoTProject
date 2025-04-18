package com.smartcampus.back.chat.dto.response;

import com.smartcampus.back.chat.entity.ChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 상세 응답 DTO
 *
 * 채팅방 생성 직후 또는 단건 조회 시 사용됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

    @Schema(description = "채팅방 ID", example = "1001")
    private Long roomId;

    @Schema(description = "채팅방 제목", example = "자바 스터디 모집방")
    private String title;

    @Schema(description = "채팅방 유형 (POST, GROUP, STUDY 등)", example = "POST")
    private String roomType;

    @Schema(description = "연동 객체 ID (게시글, 그룹 등)", example = "42")
    private Long refId;

    @Schema(description = "참여 중인 사용자 수", example = "3")
    private int participantCount;

    @Schema(description = "채팅방 생성 시각", example = "2025-04-18T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "마지막 메시지 전송 시각", example = "2025-04-18T18:05:00")
    private LocalDateTime lastMessageAt;

    @Schema(description = "현재 로그인 사용자의 입장 여부", example = "true")
    private boolean joined;

    /**
     * ChatRoom 엔티티를 기반으로 응답 DTO 생성
     *
     * @param room ChatRoom 엔티티
     * @return ChatRoomResponse DTO
     */
    public static ChatRoomResponse fromEntity(ChatRoom room) {
        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .title(room.getTitle())
                .roomType(room.getRoomType())
                .refId(room.getRefId())
                .createdAt(room.getCreatedAt())
                .lastMessageAt(room.getLastMessageAt())
                .participantCount(room.getParticipants() != null ? room.getParticipants().size() : 0)
                .joined(false) // 기본값 false, 실제 요청자 여부는 컨텍스트에 따라 채워야 함
                .build();
    }
}
