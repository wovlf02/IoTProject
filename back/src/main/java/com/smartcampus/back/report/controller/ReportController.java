package com.smartcampus.back.report.controller;

import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.report.dto.request.ReportRequest;
import com.smartcampus.back.report.dto.response.ReportResponse;
import com.smartcampus.back.report.service.ReportService;
import com.smartcampus.back.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 신고 요청을 처리하는 컨트롤러
 * <p>
 * 게시글, 댓글, 대댓글, 사용자에 대한 신고 생성 API를 제공
 * </p>
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * 게시글 신고 요청
     *
     * @param postId  신고 대상 게시글 ID
     * @param request 신고 요청 정보
     * @param user    현재 로그인 사용자
     * @return 신고 처리 결과
     */
    @PostMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<ReportResponse>> reportPost(
            @PathVariable Long postId,
            @RequestBody ReportRequest request,
            @CurrentUser User user
    ) {
        ReportResponse response = reportService.reportPost(postId, request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 댓글 신고 요청
     *
     * @param commentId 신고 대상 댓글 ID
     * @param request   신고 요청 정보
     * @param user      현재 로그인 사용자
     * @return 신고 처리 결과
     */
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<ReportResponse>> reportComment(
            @PathVariable Long commentId,
            @RequestBody ReportRequest request,
            @CurrentUser User user
    ) {
        ReportResponse response = reportService.reportComment(commentId, request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 대댓글 신고 요청
     *
     * @param replyId 신고 대상 대댓글 ID
     * @param request 신고 요청 정보
     * @param user    현재 로그인 사용자
     * @return 신고 처리 결과
     */
    @PostMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponse<ReportResponse>> reportReply(
            @PathVariable Long replyId,
            @RequestBody ReportRequest request,
            @CurrentUser User user
    ) {
        ReportResponse response = reportService.reportReply(replyId, request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 사용자 신고 요청
     *
     * @param targetUserId 신고 대상 사용자 ID
     * @param request      신고 요청 정보
     * @param user         현재 로그인 사용자
     * @return 신고 처리 결과
     */
    @PostMapping("/users/{targetUserId}")
    public ResponseEntity<ApiResponse<ReportResponse>> reportUser(
            @PathVariable Long targetUserId,
            @RequestBody ReportRequest request,
            @CurrentUser User user
    ) {
        ReportResponse response = reportService.reportUser(targetUserId, request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
