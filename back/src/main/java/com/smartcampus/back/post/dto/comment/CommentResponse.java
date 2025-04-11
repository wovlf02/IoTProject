package com.smartcampus.back.post.dto.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 응답 DTO
 * 댓글 작성 또는 조회 시 클라이언트에 반환되는 데이터 구조
 */
@Getter
@Builder
public class CommentResponse {

    /**
     * 댓글 ID
     */
    private Long commentId;

    /**
     * 댓글 내용
     */
    private String content;

    /**
     * 댓글 작성자 ID
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
