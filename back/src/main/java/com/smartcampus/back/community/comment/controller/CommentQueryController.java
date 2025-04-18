package com.smartcampus.back.community.comment.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.community.comment.dto.response.CommentListResponse;
import com.smartcampus.back.community.comment.service.CommentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 및 대댓글 조회 전용 API 컨트롤러
 * <p>
 * 게시글 ID 기준으로 전체 댓글 트리 구조를 반환합니다.
 * 차단된 댓글은 제외되며, 정렬 기준은 기본적으로 생성일입니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    /**
     * 게시글 기준 전체 댓글 및 대댓글 목록 조회
     *
     * @param postId 게시글 ID
     * @param user   현재 로그인 사용자 (차단 필터링용)
     * @return 계층형 댓글 응답
     */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentListResponse> getCommentsByPost(
            @PathVariable Long postId,
            @CurrentUser User user
    ) {
        CommentListResponse response = commentQueryService.getCommentsByPost(postId, user);
        return ResponseEntity.ok(response);
    }
}
