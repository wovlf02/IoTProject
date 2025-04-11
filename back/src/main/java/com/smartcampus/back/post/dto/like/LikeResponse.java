package com.smartcampus.back.post.dto.like;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 좋아요 응답 DTO
 * 좋아요 처리 결과와 좋아요 개수 정보를 클라이언트에 반환
 */
@Getter
@Builder
public class LikeResponse {

    /**
     * 좋아요 처리 결과 메시지
     */
    private String message;

    /**
     * 현재 게시글의 총 좋아요 수
     */
    private int totalLikes;

    /**
     * 현재 사용자가 좋아요를 누른 상태인지 여부
     */
    private boolean liked;
}
