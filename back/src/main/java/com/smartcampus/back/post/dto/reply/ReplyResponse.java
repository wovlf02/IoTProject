package com.smartcampus.back.post.dto.reply;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 대댓글(답글) 응답 DTO
 * 대댓글 작성/수정/조회 시 클라이언트에게 반환되는 데이터 구조
 */
@Getter
@Builder
public class ReplyResponse {

    /**
     * 대댓글 ID (고유 식별자)
     */
    private Long replyId;

    /**
     * 대댓글 본문 내용
     */
    private String content;

    /**
     * 작성자의 사용자 ID
     */
    private Long writerId;

    /**
     * 대댓글 최초 작성 시각
     */
    private LocalDateTime createdAt;

    /**
     * 대댓글 최근 수정 시각
     */
    private LocalDateTime updatedAt;

    /**
     * 현재 사용자가 이 대댓글에 좋아요를 눌렀는지 여부
     * true: 좋아요 누름 / false: 좋아요 안 누름
     */
    private boolean liked;

    /**
     * 해당 대댓글의 총 좋아요 수
     */
    private int likeCount;

    /**
     * 대댓글 작성자와 1:1 채팅을 시작할 수 있는 URI
     * 예: /api/chat/start?userId={작성자 ID}
     */
    private String chatEntryUrl;
}
