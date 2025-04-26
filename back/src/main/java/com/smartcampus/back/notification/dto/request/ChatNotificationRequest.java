package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatNotificationRequest
 * <p>
 * 채팅 메시지 수신 시 푸시 알림을 전송하기 위한 요청 객체입니다.
 * 채팅방 ID, 수신자 ID, 알림 제목, 알림 본문 메시지를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class ChatNotificationRequest {

    /**
     * 수신할 사용자의 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long targetUserId;

    /**
     * 채팅방 ID
     */
    @NotNull(message = "채팅방 ID는 필수입니다.")
    private Long roomId;

    /**
     * 알림 제목
     */
    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    /**
     * 알림 본문
     */
    @NotBlank(message = "알림 본문은 필수입니다.")
    private String body;

    @Builder
    public ChatNotificationRequest(Long targetUserId, Long roomId, String title, String body) {
        this.targetUserId = targetUserId;
        this.roomId = roomId;
        this.title = title;
        this.body = body;
    }
}
