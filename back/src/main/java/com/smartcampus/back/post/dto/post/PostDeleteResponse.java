package com.smartcampus.back.post.dto.post;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 삭제 응답 DTO
 * 게시글 삭제 후 처리 결과 메시지 반환
 */
@Getter
@Builder
public class PostDeleteResponse {

    /**
     * 삭제된 게시글 ID
     */
    private Long postId;

    /**
     * 처리 결과 메시지
     */
    private String message;
}
