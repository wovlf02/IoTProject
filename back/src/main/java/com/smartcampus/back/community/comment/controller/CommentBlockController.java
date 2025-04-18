package com.smartcampus.back.community.comment.controller;

import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.service.CommentBlockService;
import com.smartcampus.back.community.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 및 대댓글 차단/해제 기능을 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/community/comments")
@RequiredArgsConstructor
public class CommentBlockController {

    private final CommentBlockService commentBlockService;
    private final CommentService commentService;

    /**
     * 댓글 또는 대댓글 차단
     */
    @PostMapping("/{commentId}/block")
    public ResponseEntity<ApiResponse<?>> blockComment(
            @CurrentUser User user,
            @PathVariable Long commentId) {
        commentBlockService.blockComment(user, commentId);
        return ResponseEntity.ok(ApiResponse.success("댓글이 차단되었습니다."));
    }

    /**
     * 댓글 또는 대댓글 차단 해제
     */
    @DeleteMapping("/{commentId}/block")
    public ResponseEntity<ApiResponse<?>> unblockComment(
            @CurrentUser User user,
            @PathVariable Long commentId) {
        commentBlockService.unblockComment(user, commentId);
        return ResponseEntity.ok(ApiResponse.success("댓글 차단이 해제되었습니다."));
    }

    /**
     * 댓글 또는 대댓글이 차단되었는지 확인
     */
    @GetMapping("/{commentId}/block")
    public ResponseEntity<ApiResponse<Boolean>> isCommentBlocked(
            @CurrentUser User user,
            @PathVariable Long commentId) {

        Comment comment = commentService.getCommentById(commentId); // 또는 내부에서 findById
        boolean blocked = commentBlockService.isBlocked(user, comment);
        return ResponseEntity.ok(ApiResponse.success(blocked));
    }
}
