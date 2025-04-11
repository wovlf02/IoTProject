package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.comment.CommentCreateRequest;
import com.smartcampus.back.post.dto.comment.CommentResponse;
import com.smartcampus.back.post.dto.comment.CommentUpdateRequest;
import com.smartcampus.back.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 관련 요청을 처리하는 REST 컨트롤러
 * 댓글 생성, 수정, 삭제 기능 제공
 */
@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param postId 댓글을 작성할 게시글 ID
     * @param request 댓글 작성 요청 데이터 (내용, 작성자 등)
     * @return 생성된 댓글 정보
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.createComment(postId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 댓글 수정
     *
     * @param postId 댓글이 속한 게시글 ID
     * @param commentId 수정할 댓글의 ID
     * @param request 댓글 수정 요청 데이터
     * @return 수정된 댓글 정보
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse response = commentService.updateComment(postId, commentId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 댓글 삭제
     *
     * @param postId 댓글이 속한 게시글 ID
     * @param commentId 삭제할 댓글의 ID
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
