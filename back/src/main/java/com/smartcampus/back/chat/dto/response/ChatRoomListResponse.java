package com.smartcampus.back.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 목록 응답 DTO
 *
 * 사용자가 속한 채팅방 리스트 조회 시 사용됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListResponse {

    @Schema(description = "채팅방 ID", example = "1001")
    private Long roomId;

    @Schema(description = "채팅방 제목", example = "자바 스터디 모집")
    private String title;

    @Schema(description = "채팅방 유형 (POST, STUDY, GROUP 등)", example = "POST")
    private String roomType;

    @Schema(description = "연동 객체 ID (게시글, 그룹 등)", example = "73")
    private Long refId;

    @Schema(description = "마지막 메시지 내용", example = "안녕하세요~ 내일 시간 될까요?")
    private String lastMessage;

    @Schema(description = "마지막 메시지 전송 시간", example = "2025-04-18T14:50:00")
    private LocalDateTime lastSentAt;

    @Schema(description = "현재 입장자 수", example = "3")
    private int participantCount;

    @Schema(description = "읽지 않은 메시지 수", example = "5")
    private int unreadCount;

    @Schema(description = "마지막 메시지가 본인인지 여부", example = "false")
    private boolean isLastMessageMine;
}
