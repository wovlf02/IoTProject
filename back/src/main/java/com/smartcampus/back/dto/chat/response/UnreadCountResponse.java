package com.smartcampus.back.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 채팅방별 안읽은 메시지 수를 응답하는 DTO입니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class UnreadCountResponse {

    /**
     * 채팅방 ID
     */
    private Long roomId;

    /**
     * 안읽은 메시지 수
     */
    private long unreadCount;
}
