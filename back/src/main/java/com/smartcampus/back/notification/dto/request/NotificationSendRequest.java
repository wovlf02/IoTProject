package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FCM 알림 전송 요청 DTO
 * <p>
 * 알림을 보낼 사용자 정보와 알림 제목, 내용을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSendRequest {

    /**
     * 수신 대상 사용자 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long receiverId;

    /**
     * 알림 제목
     */
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    /**
     * 알림 내용
     */
    @NotBlank(message = "내용은 필수입니다.")
    private String body;

    /**
     * 알림 클릭 시 이동할 URL (선택사항)
     */
    private String clickAction;

    /**
     * 알림 유형 (예: 채팅, 친구요청, 시스템 등)
     */
    private String type;

    /**
     * 관련 리소스 ID (예: 게시글 ID, 채팅방 ID 등)
     */
    private Long referenceId;
}
