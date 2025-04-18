package com.smartcampus.back.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 인기 게시글 목록 응답 DTO
 * <p>
 * 좋아요 수, 댓글 수, 조회수 등을 기준으로 정렬된 인기 게시글 리스트를 반환합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularPostListResponse {

    /**
     * 인기 게시글 리스트
     */
    private List<PostResponse> popularPosts;

    /**
     * 인기글 개수
     */
    private int totalCount;

    /**
     * 정적 팩토리 메서드 for builder
     *
     * @param posts 인기 게시글 리스트
     * @return 응답 객체
     */
    public static PopularPostListResponse of(List<PostResponse> posts) {
        return PopularPostListResponse.builder()
                .popularPosts(posts)
                .totalCount(posts.size())
                .build();
    }
}
