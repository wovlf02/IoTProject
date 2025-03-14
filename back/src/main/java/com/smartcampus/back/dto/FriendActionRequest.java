package com.smartcampus.back.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 친구 요청 수락/거절 DTO
 * 사용자가 친구 요청을 수락하거나 거절할 때 사용됨
 */
@Getter
@Setter
@NoArgsConstructor
public class FriendActionRequest {

    /**
     * 요청을 보낸 사용자 ID
     * 친구 요청을 보낸 사용자의 ID
     */
    @NotNull(message = "친구 요청을 보낸 사용자 ID가 필요합니다.")
    private Long senderId;

    /**
     * 요청을 받은 사용자 ID
     * 친구 요청을 받은 사용자의 ID
     */
    @NotNull(message = "친구 요청을 받은 사용자 ID가 필요합니다.")
    private Long receiverId;
}
