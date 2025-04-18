package com.smartcampus.back.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 사용자에게 전달될 알림 목록 응답 DTO
 * <p>
 * 개별 알림 응답 객체(NotificationResponse)를 리스트로 포함합니다.
 * </p>
 */
@Getter
@AllArgsConstructor
@Builder
public class NotificationListResponse {

    /**
     * 알림 목록
     */
    private List<NotificationResponse> notifications;

    /**
     * 알림 전체 개수
     */
    private int totalCount;

    /**
     * 생성자 편의 메서드
     *
     * @param notifications 알림 리스트
     * @return NotificationListResponse 객체
     */
    public static NotificationListResponse from(List<NotificationResponse> notifications) {
        return NotificationListResponse.builder()
                .notifications(notifications)
                .totalCount(notifications.size())
                .build();
    }
}
