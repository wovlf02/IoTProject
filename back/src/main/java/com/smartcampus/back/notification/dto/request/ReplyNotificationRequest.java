package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReplyNotificationRequest
 * <p>
 * 댓글에 대댓글이 달렸을 때 알림을 전송하기 위한 요청 객체입니다.
 * 수신자 ID, 댓글 ID, 알림 제목, 알림 본문을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class ReplyNotificationRequest {

    /**
     * 수신할 사용자의 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long targetUserId;

    /**
     * 대댓글이 달린 댓글 ID
     */
    @NotNull(message = "댓글 ID는 필수입니다.")
    private Long commentId;

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
    public ReplyNotificationRequest(Long targetUserId, Long commentId, String title, String body) {
        this.targetUserId = targetUserId;
        this.commentId = commentId;
        this.title = title;
        this.body = body;
    }
}
