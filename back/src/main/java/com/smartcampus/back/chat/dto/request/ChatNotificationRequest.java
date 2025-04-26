package com.smartcampus.back.chat.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatNotificationRequest
 * <p>
 * 채팅방 알림 설정(ON/OFF) 변경 요청 시 사용하는 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class ChatNotificationRequest {

    /**
     * 알림 활성화 여부 (true = 알림 ON, false = 알림 OFF)
     */
    @NotNull(message = "알림 설정 값을 입력해 주세요.")
    private Boolean notificationsEnabled;
}
