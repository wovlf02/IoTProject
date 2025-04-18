package com.smartcampus.back.community.post.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.community.post.dto.request.PostCreateRequest;
import com.smartcampus.back.community.post.dto.request.PostUpdateRequest;
import com.smartcampus.back.community.post.dto.response.PostSimpleResponse;
import com.smartcampus.back.community.post.service.PostFavoriteService;
import com.smartcampus.back.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 작성, 수정, 삭제, 즐겨찾기, 좋아요 등의 요청을 처리하는 REST 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/posts")
public class PostController {

    private final PostService postService;
    private final PostFavoriteService postFavoriteService;

    /**
     * 게시글 작성
     *
     * @param user 로그인 사용자 정보
     * @param request 게시글 생성 요청 DTO
     * @return 생성된 게시글 ID 및 메시지 응답
     */
    @PostMapping
    public ResponseEntity<PostSimpleResponse> createPost(
            @CurrentUser User user,
            @ModelAttribute @Valid PostCreateRequest request
    ) {
        return ResponseEntity.ok(postService.createPost(user, request));
    }

    /**
     * 게시글 수정
     *
     * @param postId 수정할 게시글 ID
     * @param user 로그인 사용자 정보
     * @param request 게시글 수정 요청 DTO
     * @return 수정 결과 메시지 응답
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostSimpleResponse> updatePost(
            @PathVariable Long postId,
            @CurrentUser User user,
            @ModelAttribute @Valid PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, user, request));
    }

    /**
     * 게시글 삭제
     *
     * @param postId 삭제할 게시글 ID
     * @param user 로그인 사용자 정보
     * @return 삭제 결과 메시지 응답
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostSimpleResponse> deletePost(
            @PathVariable Long postId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(postService.deletePost(postId, user));
    }

    /**
     * 게시글 즐겨찾기 등록
     *
     * @param postId 게시글 ID
     * @param user 로그인 사용자
     * @return 등록 결과 응답
     */
    @PostMapping("/{postId}/favorite")
    public ResponseEntity<PostSimpleResponse> addFavorite(
            @PathVariable Long postId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(postFavoriteService.addFavorite(postId, user));
    }

    /**
     * 게시글 즐겨찾기 해제
     *
     * @param postId 게시글 ID
     * @param user 로그인 사용자
     * @return 삭제 결과 응답
     */
    @DeleteMapping("/{postId}/favorite")
    public ResponseEntity<PostSimpleResponse> removeFavorite(
            @PathVariable Long postId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(postFavoriteService.removeFavorite(postId, user));
    }
}
