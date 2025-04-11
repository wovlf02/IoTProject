package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.reply.ReplyResponse;
import com.smartcampus.back.post.dto.report.ReportRequest;
import com.smartcampus.back.post.dto.report.ReportResponse;
import com.smartcampus.back.post.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글, 댓글, 대댓글에 대한 신고 요청을 처리하는 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * 게시글 신고
     * 
     * @param postId 신고 대상 게시글 ID
     * @param request 신고 요청 데이터 (신고 사유, 신고자 ID 등)
     * @return 신고 처리 결과 응답
     */
    @PostMapping("/posts/{postId}")
    public ResponseEntity<ReportResponse> reportPost(
            @PathVariable Long postId,
            @RequestBody ReportRequest request
    ) {
        ReportResponse response = reportService.reportPost(postId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 신고
     * 
     * @param commentId 신고 대상 댓글 ID
     * @param request 신고 요청 데이터
     * @return 신고 처리 결과 응답
     */
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<ReportResponse> reportComment(
            @PathVariable Long commentId,
            @RequestBody ReportRequest request
    ) {
        ReportResponse response = reportService.reportComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 대댓글 신고
     * 
     * @param replyId 신고 대상 대댓글 ID
     * @param request 신고 요청 데이터
     * @return 신고 처리 결과 응답
     */
    @PostMapping("/replies/{replyId}")
    public ResponseEntity<ReportResponse> reportReply(
            @PathVariable Long replyId,
            @RequestBody ReportRequest request
    ) {
        ReportResponse response = reportService.reportReply(replyId, request);
        return ResponseEntity.ok(response);
    }
}
