package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * NotificationReadRequest
 * <p>
 * 여러 개의 알림을 선택하여 읽음 처리할 때 사용하는 요청 객체입니다.
 * 읽음 처리할 알림 ID 리스트를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class NotificationReadRequest {

    /**
     * 읽음 처리할 알림 ID 리스트
     */
    @NotEmpty(message = "읽음 처리할 알림 ID 리스트는 비어있을 수 없습니다.")
    private List<Long> notificationIds;

    @Builder
    public NotificationReadRequest(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}
