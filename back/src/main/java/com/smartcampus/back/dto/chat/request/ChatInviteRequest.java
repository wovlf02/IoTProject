package com.smartcampus.back.dto.chat.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

/**
 * 채팅방에 사용자(들)를 초대할 때 사용하는 요청 DTO입니다.
 */
@Getter
public class ChatInviteRequest {

    /**
     * 초대할 사용자 ID 목록
     */
    @NotEmpty(message = "초대할 사용자 ID 목록은 비어 있을 수 없습니다.")
    private List<Long> userIds;
}
