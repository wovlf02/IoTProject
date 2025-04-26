package com.smartcampus.back.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CommentNotificationRequest
 * <p>
 * 게시글에 댓글이 달렸을 때 알림을 전송하기 위한 요청 객체입니다.
 * 수신자 ID, 게시글 ID, 알림 제목, 알림 본문을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class CommentNotificationRequest {

    /**
     * 수신할 사용자의 ID
     */
    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long targetUserId;

    /**
     * 댓글이 달린 게시글 ID
     */
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long postId;

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
    public CommentNotificationRequest(Long targetUserId, Long postId, String title, String body) {
        this.targetUserId = targetUserId;
        this.postId = postId;
        this.title = title;
        this.body = body;
    }
}
