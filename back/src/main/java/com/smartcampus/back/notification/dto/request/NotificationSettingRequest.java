package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * NotificationSettingRequest
 * <p>
 * 사용자가 알림 수신 설정(ON/OFF)을 변경할 때 사용하는 요청 객체입니다.
 * 각 알림 종류별 수신 여부를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class NotificationSettingRequest {

    /**
     * 댓글 알림 수신 여부
     */
    @NotNull(message = "댓글 알림 설정은 필수입니다.")
    private Boolean commentNotificationEnabled;

    /**
     * 대댓글 알림 수신 여부
     */
    @NotNull(message = "대댓글 알림 설정은 필수입니다.")
    private Boolean replyNotificationEnabled;

    /**
     * 친구 요청/수락 알림 수신 여부
     */
    @NotNull(message = "친구 알림 설정은 필수입니다.")
    private Boolean friendNotificationEnabled;

    /**
     * 채팅 메시지 수신 알림 수신 여부
     */
    @NotNull(message = "채팅 알림 설정은 필수입니다.")
    private Boolean chatNotificationEnabled;

    /**
     * 다음 수업 시작 알림 수신 여부
     */
    @NotNull(message = "다음 수업 알림 설정은 필수입니다.")
    private Boolean nextClassNotificationEnabled;

    /**
     * 지각 경고 알림 수신 여부
     */
    @NotNull(message = "지각 경고 알림 설정은 필수입니다.")
    private Boolean lateWarningNotificationEnabled;

    @Builder
    public NotificationSettingRequest(
            Boolean commentNotificationEnabled,
            Boolean replyNotificationEnabled,
            Boolean friendNotificationEnabled,
            Boolean chatNotificationEnabled,
            Boolean nextClassNotificationEnabled,
            Boolean lateWarningNotificationEnabled
    ) {
        this.commentNotificationEnabled = commentNotificationEnabled;
        this.replyNotificationEnabled = replyNotificationEnabled;
        this.friendNotificationEnabled = friendNotificationEnabled;
        this.chatNotificationEnabled = chatNotificationEnabled;
        this.nextClassNotificationEnabled = nextClassNotificationEnabled;
        this.lateWarningNotificationEnabled = lateWarningNotificationEnabled;
    }
}
