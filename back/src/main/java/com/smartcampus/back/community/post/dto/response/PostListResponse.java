package com.smartcampus.back.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 게시글 목록 조회 응답 DTO
 * <p>
 * 게시글 리스트와 페이징 정보 등을 포함하여 클라이언트에 전달합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {

    /**
     * 게시글 리스트 (간략 정보)
     */
    private List<PostSimpleResponse> posts;

    /**
     * 전체 게시글 수
     */
    private long totalCount;

    /**
     * 현재 페이지 번호
     */
    private int page;

    /**
     * 페이지당 항목 수
     */
    private int size;

    /**
     * 총 페이지 수
     */
    private int totalPages;

    /**
     * 정적 팩토리 메서드
     *
     * @param posts 게시글 리스트
     * @param totalCount 전체 게시글 수
     * @param page 현재 페이지 번호
     * @param size 페이지 크기
     * @return 응답 객체
     */
    public static PostListResponse of(List<PostSimpleResponse> posts, long totalCount, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return PostListResponse.builder()
                .posts(posts)
                .totalCount(totalCount)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .build();
    }
}
