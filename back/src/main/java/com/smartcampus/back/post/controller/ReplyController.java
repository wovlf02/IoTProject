package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.reply.ReplyCreateRequest;
import com.smartcampus.back.post.dto.reply.ReplyResponse;
import com.smartcampus.back.post.dto.reply.ReplyUpdateRequest;
import com.smartcampus.back.post.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 대댓글(답글) 관련 요청을 처리하는 REST 컨트롤러
 * 대댓글 생성, 수정, 삭제 기능 제공
 */
@RestController
@RequestMapping("/api/posts/{postId}/comments/{commentId}/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 특정 댓글에 대댓글(답글) 작성
     *
     * @param postId 대댓글(답글)을 작성할 게시글 ID
     * @param commentId 대댓글(답글)을 작성할 댓글 ID
     * @param request 대댓글 작성 요청 데이터
     * @return 생성된 대댓글 정보
     */
    @PostMapping
    public ResponseEntity<ReplyResponse> createReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody ReplyCreateRequest request
    ) {
        ReplyResponse response = replyService.createReply(postId, commentId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 대댓글 수정
     *
     * @param postId 대댓글이 속한 게시글 ID
     * @param commentId 대댓글이 속한 댓글 ID
     * @param replyId 수정할 대댓글 ID
     * @param request 대댓글 수정 요청 데이터
     * @return 수정된 대댓글 정보
     */
    @PutMapping("/{replyId}")
    public ResponseEntity<ReplyResponse> updateReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @PathVariable Long replyId,
            @RequestBody ReplyUpdateRequest request
    ) {
        ReplyResponse response = replyService.updateReply(postId, commentId, replyId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 대댓글 삭제
     *
     * @param postId 대댓글이 속한 게시글 ID
     * @param commentId 대댓글이 속한 댓글 ID
     * @param replyId 삭제할 대댓글 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @PathVariable Long replyId
    ) {
        replyService.deleteReply(postId, commentId, replyId);
        return ResponseEntity.ok("대댓글이 성공적으로 삭제되었습니다.");
    }
}
