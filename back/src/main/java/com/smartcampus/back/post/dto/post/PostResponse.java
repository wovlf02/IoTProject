package com.smartcampus.back.post.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시글 목록 출력용 DTO
 * 게시글 리스트 화면에서 사용되는 요약 정보 구조
 */
@Getter
@Builder
public class PostResponse {

    /**
     * 게시글 ID
     */
    private Long postId;

    /**
     * 제목
     */
    private String title;

    /**
     * 작성자 ID
     */
    private Long writerId;

    /**
     * 조회수
     */
    private int viewCount;

    /**
     * 작성일시
     */
    private LocalDateTime createdAt;

    /**
     * 좋아요 수
     */
    private int likeCount;

    /**
     * 댓글 수
     */
    private int commentCount;
}
