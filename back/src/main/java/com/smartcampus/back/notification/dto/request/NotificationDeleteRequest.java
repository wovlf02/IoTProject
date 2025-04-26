package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * NotificationDeleteRequest
 * <p>
 * 여러 개의 알림을 선택하여 삭제할 때 사용하는 요청 객체입니다.
 * 삭제할 알림 ID 리스트를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class NotificationDeleteRequest {

    /**
     * 삭제할 알림 ID 리스트
     */
    @NotEmpty(message = "삭제할 알림 ID 리스트는 비어있을 수 없습니다.")
    private List<Long> notificationIds;

    @Builder
    public NotificationDeleteRequest(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}
