package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FriendNotificationRequest
 * <p>
 * 친구 요청 또는 친구 수락 시 알림을 전송하기 위한 요청 객체입니다.
 * 수신자 ID, 친구 ID, 알림 제목, 알림 본문을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class FriendNotificationRequest {

    /**
     * 수신할 사용자의 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long targetUserId;

    /**
     * 친구 요청 또는 수락을 한 친구의 사용자 ID
     */
    @NotNull(message = "친구 ID는 필수입니다.")
    private Long friendId;

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
    public FriendNotificationRequest(Long targetUserId, Long friendId, String title, String body) {
        this.targetUserId = targetUserId;
        this.friendId = friendId;
        this.title = title;
        this.body = body;
    }
}
