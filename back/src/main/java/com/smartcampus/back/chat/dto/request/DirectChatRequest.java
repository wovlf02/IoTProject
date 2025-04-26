package com.smartcampus.back.chat.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DirectChatRequest
 * <p>
 * 친구와 1:1 채팅방을 생성할 때 사용하는 요청 DTO입니다.
 * (친구의 사용자 ID를 입력받습니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class DirectChatRequest {

    /**
     * 채팅할 상대방(친구)의 사용자 ID
     */
    @NotNull(message = "상대방 사용자 ID를 입력해 주세요.")
    private Long targetUserId;
}
