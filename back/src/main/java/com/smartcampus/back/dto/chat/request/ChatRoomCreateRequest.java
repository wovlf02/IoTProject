package com.smartcampus.back.dto.chat.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

/**
 * 채팅방 생성 요청을 위한 DTO입니다.
 * 그룹 채팅 또는 1:1 채팅 방식을 구분하여 생성할 수 있습니다.
 */
@Getter
public class ChatRoomCreateRequest {

    /**
     * 채팅방 이름 (그룹 채팅일 경우 필수)
     */
    @NotBlank(message = "채팅방 이름은 필수입니다.")
    private String roomName;

    /**
     * 그룹 채팅 여부 (true: 그룹, false: 1:1)
     */
    @NotNull(message = "그룹 채팅 여부는 필수입니다.")
    private Boolean group;

    /**
     * 1:1 채팅 대상 사용자 ID (그룹 채팅이 아닌 경우 필수)
     */
    private Long targetUserId;

    /**
     * 그룹 채팅 대상 사용자 ID 목록 (본인은 제외)
     */
    private List<Long> userIds;
}
