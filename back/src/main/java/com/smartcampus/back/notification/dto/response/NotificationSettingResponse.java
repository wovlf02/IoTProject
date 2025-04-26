package com.smartcampus.back.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * NotificationSettingResponse
 * <p>
 * 사용자의 알림 수신 설정(ON/OFF) 상태를 조회할 때 사용하는 응답 객체입니다.
 * 댓글, 대댓글, 친구, 채팅, 수업, 지각 알림 각각에 대한 설정 여부를 제공합니다.
 * </p>
 */
@Getter
@Builder
public class NotificationSettingResponse {

    /**
     * 댓글 알림 수신 여부
     */
    private Boolean commentNotificationEnabled;

    /**
     * 대댓글 알림 수신 여부
     */
    private Boolean replyNotificationEnabled;

    /**
     * 친구 요청/수락 알림 수신 여부
     */
    private Boolean friendNotificationEnabled;

    /**
     * 채팅 메시지 수신 알림 수신 여부
     */
    private Boolean chatNotificationEnabled;

    /**
     * 다음 수업 시작 알림 수신 여부
     */
    private Boolean nextClassNotificationEnabled;

    /**
     * 지각 경고 알림 수신 여부
     */
    private Boolean lateWarningNotificationEnabled;
}
