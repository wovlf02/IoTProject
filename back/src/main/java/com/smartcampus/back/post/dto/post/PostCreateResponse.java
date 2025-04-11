package com.smartcampus.back.post.dto.post;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 생성 응답 DTO
 * 게시글 작성 후 클라이언트에게 전달되는 응답 데이터
 */
@Getter
@Builder
public class PostCreateResponse {

    /**
     * 생성된 게시글 ID
     */
    private Long postId;

    /**
     * 응답 메시지
     */
    private String message;
}
