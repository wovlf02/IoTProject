package com.smartcampus.back.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 즐겨찾기한 게시글 목록 응답 DTO
 * <p>
 * 사용자가 즐겨찾기한 게시글 목록을 반환할 때 사용됩니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritePostListResponse {

    /**
     * 즐겨찾기된 게시글 목록
     */
    private List<PostResponse> favoritePosts;

    /**
     * 전체 즐겨찾기 개수
     */
    private int totalCount;

    public static FavoritePostListResponse of(List<PostResponse> posts) {
        return FavoritePostListResponse.builder()
                .favoritePosts(posts)
                .totalCount(posts.size())
                .build();
    }
}
