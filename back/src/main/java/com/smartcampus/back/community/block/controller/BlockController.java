package com.smartcampus.back.community.block.controller;

import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.community.block.service.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 차단 관련 기능 (게시글, 댓글, 대댓글 차단/해제)을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class BlockController {

    private final BlockService blockService;

    /**
     * 게시글 차단
     */
    @PostMapping("/posts/{postId}/block")
    public ResponseEntity<ApiResponse<?>> blockPost(@PathVariable Long postId) {
        blockService.blockPost(postId);
        return ResponseEntity.ok(ApiResponse.success("해당 게시글이 차단되었습니다."));
    }

    /**
     * 게시글 차단 해제
     */
    @DeleteMapping("/posts/{postId}/block")
    public ResponseEntity<ApiResponse<?>> unblockPost(@PathVariable Long postId) {
        blockService.unblockPost(postId);
        return ResponseEntity.ok(ApiResponse.success("게시글 차단이 해제되었습니다."));
    }

    /**
     * 댓글 차단
     */
    @PostMapping("/comments/{commentId}/block")
    public ResponseEntity<ApiResponse<?>> blockComment(@PathVariable Long commentId) {
        blockService.blockComment(commentId);
        return ResponseEntity.ok(ApiResponse.success("해당 댓글이 차단되었습니다."));
    }

    /**
     * 댓글 차단 해제
     */
    @DeleteMapping("/comments/{commentId}/block")
    public ResponseEntity<ApiResponse<?>> unblockComment(@PathVariable Long commentId) {
        blockService.unblockComment(commentId);
        return ResponseEntity.ok(ApiResponse.success("댓글 차단이 해제되었습니다."));
    }

    /**
     * 대댓글 차단
     */
    @PostMapping("/replies/{replyId}/block")
    public ResponseEntity<ApiResponse<?>> blockReply(@PathVariable Long replyId) {
        blockService.blockReply(replyId);
        return ResponseEntity.ok(ApiResponse.success("해당 대댓글이 차단되었습니다."));
    }

    /**
     * 대댓글 차단 해제
     */
    @DeleteMapping("/replies/{replyId}/block")
    public ResponseEntity<ApiResponse<?>> unblockReply(@PathVariable Long replyId) {
        blockService.unblockReply(replyId);
        return ResponseEntity.ok(ApiResponse.success("대댓글 차단이 해제되었습니다."));
    }
}
