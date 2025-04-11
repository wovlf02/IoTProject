package com.smartcampus.back.post.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 좋아요 요청 DTO
 * 사용자가 특정 게시글에 좋아요(추천)를 요청할 때 사용되는 데이터 구조
 */
@Getter
@Setter
public class LikeRequest {

    /**
     * 좋아요를 누른 사용자 ID
     */
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    /**
     * 좋아요를 누를 대상 게시글 ID
     */
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long postId;
}
