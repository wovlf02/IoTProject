package com.smartcampus.back.community.post.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.community.post.dto.response.*;
import com.smartcampus.back.community.post.service.PostFavoriteService;
import com.smartcampus.back.community.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 목록, 상세, 인기글, 즐겨찾기 등 조회 전용 API를 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;
    private final PostFavoriteService postFavoriteService;

    /**
     * 게시글 상세 조회
     *
     * @param postId 조회할 게시글 ID
     * @param user 로그인 사용자
     * @return 게시글 상세 정보
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(
            @PathVariable Long postId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(postQueryService.getPostDetail(postId, user));
    }

    /**
     * 게시글 목록 조회
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param category 카테고리 필터 (선택)
     * @return 게시글 리스트 응답
     */
    @GetMapping
    public ResponseEntity<PostListResponse> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(postQueryService.getPostList(page, size, category));
    }

    /**
     * 게시글 키워드 검색
     *
     * @param keyword 검색어
     * @param category 카테고리 필터 (선택)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 검색 결과 게시글 리스트
     */
    @GetMapping("/search")
    public ResponseEntity<PostListResponse> searchPosts(
            @RequestParam String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postQueryService.searchPosts(keyword, category, page, size));
    }

    /**
     * 인기 게시글 조회 (좋아요 수, 댓글 수, 조회수 기반)
     *
     * @return 인기 게시글 목록 응답
     */
    @GetMapping("/popular")
    public ResponseEntity<PopularPostListResponse> getPopularPosts() {
        return ResponseEntity.ok(postQueryService.getPopularPosts());
    }

    /**
     * 즐겨찾기한 게시글 목록 조회
     *
     * @param user 로그인 사용자
     * @return 즐겨찾기 게시글 목록 응답
     */
    @GetMapping("/favorites")
    public ResponseEntity<FavoritePostListResponse> getFavoritePosts(
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(postFavoriteService.getFavoritePostList(user));
    }

    /**
     * 활동 순위 조회 (글/댓글 수 기준 사용자 랭킹)
     *
     * @return 사용자 활동 랭킹 응답
     */
    @GetMapping("/ranking")
    public ResponseEntity<RankingResponse> getUserRanking() {
        return ResponseEntity.ok(postQueryService.getUserRanking());
    }
}
