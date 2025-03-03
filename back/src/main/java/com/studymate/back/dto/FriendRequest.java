package com.studymate.back.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 친구 요청 DTO
 * 사용자가 특정 사용자에게 친구 요청을 보낼 때 사용함
 */
@Getter
@Setter
@NoArgsConstructor
public class FriendRequest {

    /**
     * 요청을 보낸 사용자 ID
     * 친구 요청을 보내는 사용자의 ID
     */
    @NotNull(message = "보낸 사용자의 ID가 필요합니다.")
    private Long senderId;

    /**
     * 요청을 받는 사용자 ID
     * 친구 요청을 받는 사용자의 ID
     */
    @NotNull(message = "받는 사용자의 ID가 필요합니다.")
    private Long receiverId;
}
