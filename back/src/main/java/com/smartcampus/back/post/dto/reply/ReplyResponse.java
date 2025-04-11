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
     * 대댓글 ID
     */
    private Long replyId;

    /**
     * 내용
     */
    private String content;

    /**
     * 작성자 ID
     */
    private Long writerId;

    /**
     * 작성 시각
     */
    private LocalDateTime createdAt;

    /**
     * 수정 시각
     */
    private LocalDateTime updatedAt;
}
