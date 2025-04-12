package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.like.LikeResponse;
import com.smartcampus.back.post.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글/댓글/대댓글에 대한 좋아요 기능을 처리하는 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // --- 게시글 좋아요 ---

    /**
     * 게시글 좋아요 토글
     */
    @PostMapping("/posts/{postId}")
    public ResponseEntity<LikeResponse> togglePostLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        LikeResponse response = likeService.togglePostLike(postId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 좋아요 개수
     */
    @GetMapping("/posts/{postId}/count")
    public ResponseEntity<Long> getPostLikeCount(@PathVariable Long postId) {
        long count = likeService.getLikeCountByPost(postId);
        return ResponseEntity.ok(count);
    }

    /**
     * 게시글 좋아요 상태 확인
     */
    @GetMapping("/posts/{postId}/status")
    public ResponseEntity<Boolean> isPostLiked(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        boolean liked = likeService.isPostLiked(postId, userId);
        return ResponseEntity.ok(liked);
    }

    // --- 댓글 좋아요 ---

    /**
     * 댓글 좋아요 토글
     */
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<LikeResponse> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        LikeResponse response = likeService.toggleCommentLike(commentId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 좋아요 개수
     */
    @GetMapping("/comments/{commentId}/count")
    public ResponseEntity<Long> getCommentLikeCount(@PathVariable Long commentId) {
        long count = likeService.getLikeCountByComment(commentId);
        return ResponseEntity.ok(count);
    }

    /**
     * 댓글 좋아요 상태 확인
     */
    @GetMapping("/comments/{commentId}/status")
    public ResponseEntity<Boolean> isCommentLiked(
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        boolean liked = likeService.isCommentLiked(commentId, userId);
        return ResponseEntity.ok(liked);
    }

    // --- 대댓글 좋아요 ---

    /**
     * 대댓글 좋아요 토글
     */
    @PostMapping("/replies/{replyId}")
    public ResponseEntity<LikeResponse> toggleReplyLike(
            @PathVariable Long replyId,
            @RequestParam Long userId
    ) {
        LikeResponse response = likeService.toggleReplyLike(replyId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 대댓글 좋아요 개수
     */
    @GetMapping("/replies/{replyId}/count")
    public ResponseEntity<Long> getReplyLikeCount(@PathVariable Long replyId) {
        long count = likeService.getLikeCountByReply(replyId);
        return ResponseEntity.ok(count);
    }

    /**
     * 대댓글 좋아요 상태 확인
     */
    @GetMapping("/replies/{replyId}/status")
    public ResponseEntity<Boolean> isReplyLiked(
            @PathVariable Long replyId,
            @RequestParam Long userId
    ) {
        boolean liked = likeService.isReplyLiked(replyId, userId);
        return ResponseEntity.ok(liked);
    }
}
