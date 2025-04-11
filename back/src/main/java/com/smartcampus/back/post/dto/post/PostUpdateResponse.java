package com.smartcampus.back.post.dto.post;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 수정 응답 DTO
 * 게시글 수정 후 처리 결과를 클라이언트에 반환
 */
@Getter
@Builder
public class PostUpdateResponse {

    /**
     * 수정된 게시글 ID
     */
    private Long postId;

    /**
     * 처리 메시지
     */
    private String message;
}
