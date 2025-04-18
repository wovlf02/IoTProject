package com.smartcampus.back.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글 간략 응답 DTO
 * <p>
 * 게시글 목록, 인기글, 검색 결과 등에서 사용되는 요약형 DTO입니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSimpleResponse {

    /**
     * 게시글 ID
     */
    private Long postId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 카테고리
     */
    private String category;

    /**
     * 작성자 ID
     */
    private Long writerId;

    /**
     * 작성자 닉네임
     */
    private String writerNickname;

    /**
     * 작성일시
     */
    private LocalDateTime createdAt;

    /**
     * 조회수
     */
    private long viewCount;

    /**
     * 좋아요 수
     */
    private long likeCount;

    /**
     * 댓글 수
     */
    private long commentCount;

    /**
     * 현재 로그인 사용자가 좋아요를 눌렀는지 여부
     */
    private boolean likedByMe;
}
