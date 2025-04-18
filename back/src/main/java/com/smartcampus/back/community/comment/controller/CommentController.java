package com.smartcampus.back.community.comment.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.community.comment.dto.request.CommentCreateRequest;
import com.smartcampus.back.community.comment.dto.request.CommentUpdateRequest;
import com.smartcampus.back.community.comment.dto.response.CommentSimpleResponse;
import com.smartcampus.back.community.comment.service.CommentBlockService;
import com.smartcampus.back.community.comment.service.CommentLikeService;
import com.smartcampus.back.community.comment.service.CommentReportService;
import com.smartcampus.back.community.comment.service.CommentService;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 댓글 및 대댓글 관련 API를 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final CommentReportService commentReportService;
    private final CommentBlockService commentBlockService;
    private final PostService postService;

    /**
     * 게시글에 댓글 등록
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentSimpleResponse> createComment(
            @PathVariable Long postId,
            @Valid @ModelAttribute CommentCreateRequest request,
            @RequestParam(required = false) List<MultipartFile> files,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentService.createComment(postId, request, files, user));
    }

    /**
     * 댓글에 대댓글 등록
     */
    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<CommentSimpleResponse> createReply(
            @PathVariable Long commentId,
            @Valid @ModelAttribute CommentCreateRequest request,
            @RequestParam(required = false) List<MultipartFile> files,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentService.createReply(commentId, request, files, user));
    }

    /**
     * 댓글/대댓글 수정
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentSimpleResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @ModelAttribute CommentUpdateRequest request,
            @RequestParam(required = false) List<MultipartFile> files,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request, files, user));
    }

    /**
     * 댓글/대댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentSimpleResponse> deleteComment(
            @PathVariable Long commentId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentService.deleteComment(commentId, user));
    }

    /**
     * 댓글/대댓글 좋아요
     */
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<?> likeComment(
            @PathVariable Long commentId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentLikeService.like(commentId, user));
    }

    /**
     * 댓글/대댓글 좋아요 취소
     */
    @DeleteMapping("/comments/{commentId}/like")
    public ResponseEntity<?> unlikeComment(
            @PathVariable Long commentId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentLikeService.unlike(commentId, user));
    }

    /**
     * 댓글/대댓글 신고
     */
    @PostMapping("/comments/{commentId}/report")
    public ResponseEntity<?> reportComment(
            @PathVariable Long commentId,
            @RequestBody String reason,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentReportService.report(commentId, reason, user));
    }

    /**
     * 댓글/대댓글 차단
     */
    @PostMapping("/comments/{commentId}/block")
    public ResponseEntity<?> blockComment(
            @PathVariable Long commentId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentBlockService.block(commentId, user));
    }

    /**
     * 댓글/대댓글 차단 해제
     */
    @DeleteMapping("/comments/{commentId}/block")
    public ResponseEntity<?> unblockComment(
            @PathVariable Long commentId,
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(commentBlockService.unblock(commentId, user));
    }
}
