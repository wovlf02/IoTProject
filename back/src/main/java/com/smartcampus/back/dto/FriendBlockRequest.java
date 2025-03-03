package com.smartcampus.back.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 친구 차단 요청 DTO
 * 사용자가 특정 친구를 차단할때 사용됨
 */
@Getter
@Setter
@NoArgsConstructor
public class FriendBlockRequest {

    /**
     * 차단하는 사용자 ID
     * 차단을 실행하는 사용자의 ID
     */
    @NotNull(message = "차단을 실행하는 사용자 ID가 필요합니다.")
    private Long userId;

    /**
     * 차단 대상 사용자 ID
     * 차단하려는 친구의 ID
     */
    @NotNull(message = "차단할 친구의 ID가 필요합니다.")
    private Long friendId;
}
