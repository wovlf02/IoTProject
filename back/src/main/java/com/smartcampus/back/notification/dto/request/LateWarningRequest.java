package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LateWarningRequest
 * <p>
 * 지각 가능성 판단 후 경고 알림을 전송하기 위한 요청 객체입니다.
 * 수신자 ID, 수업 ID, 알림 제목, 알림 본문을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class LateWarningRequest {

    /**
     * 수신할 사용자의 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long targetUserId;

    /**
     * 수업 ID
     */
    @NotNull(message = "수업 ID는 필수입니다.")
    private Long classId;

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
    public LateWarningRequest(Long targetUserId, Long classId, String title, String body) {
        this.targetUserId = targetUserId;
        this.classId = classId;
        this.title = title;
        this.body = body;
    }
}
