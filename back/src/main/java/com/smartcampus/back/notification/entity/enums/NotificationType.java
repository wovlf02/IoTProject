package com.smartcampus.back.notification.entity.enums;

import lombok.Getter;

/**
 * NotificationType Enum
 * <p>
 * 발송되는 알림의 종류를 정의합니다.
 * 알림 종류에 따라 제목과 내용을 구분하거나, 클라이언트 표시를 다르게 할 수 있습니다.
 * </p>
 */
@Getter
public enum NotificationType {

    /**
     * 내 게시글에 댓글이 달렸을 때
     */
    COMMENT("댓글 알림"),

    /**
     * 내 댓글에 대댓글이 달렸을 때
     */
    REPLY("대댓글 알림"),

    /**
     * 친구 요청 또는 친구 수락이 발생했을 때
     */
    FRIEND("친구 알림"),

    /**
     * 채팅방에서 새로운 메시지를 수신했을 때
     */
    CHAT("채팅 알림"),

    /**
     * 다음 수업 시작 전에 발송하는 알림
     */
    NEXT_CLASS("다음 수업 알림"),

    /**
     * 지각 위험 시 발송하는 경고 알림
     */
    LATE_WARNING("지각 경고 알림");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }
}
