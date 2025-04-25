package com.smartcampus.back.admin.controller;

import com.smartcampus.back.admin.dto.request.UserResolveRequest;
import com.smartcampus.back.admin.dto.response.ReportListResponse;
import com.smartcampus.back.admin.service.AdminReportService;
import com.smartcampus.back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 전용 신고 처리 컨트롤러
 * <p>
 * 게시글, 댓글, 사용자에 대한 신고 목록 조회 및 신고 처리 기능 제공
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final AdminReportService adminReportService;

    /**
     * 게시글 신고 목록 페이징 조회
     *
     * @param pageable 페이징 정보를 담는 객체 (page, size 등)
     * @return 게시글 신고 목록을 담은 응답
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<ReportListResponse>> getPostReports(Pageable pageable) {
        ReportListResponse reports = adminReportService.getReportList("POST", pageable);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    /**
     * 댓글 신고 목록 페이징 조회
     *
     * @param pageable 페이징 정보를 담는 객체
     * @return 댓글 신고 목록을 담은 응답
     */
    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<ReportListResponse>> getCommentReports(Pageable pageable) {
        ReportListResponse reports = adminReportService.getReportList("COMMENT", pageable);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    /**
     * 사용자 신고 목록 페이징 조회
     *
     * @param pageable 페이징 정보를 담는 객체
     * @return 사용자 신고 목록을 담은 응답
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<ReportListResponse>> getUserReports(Pageable pageable) {
        ReportListResponse reports = adminReportService.getReportList("USER", pageable);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    /**
     * 게시글 신고 처리
     *
     * @param reportId 처리할 신고의 ID
     * @param request 신고 처리 요청 객체 (조치 유형, 메모 등 포함)
     * @return 성공 여부 응답
     */
    @PutMapping("/posts/{reportId}")
    public ResponseEntity<ApiResponse<Void>> resolvePostReport(
            @PathVariable Long reportId,
            @RequestBody UserResolveRequest request
    ) {
        adminReportService.resolveReport(reportId, "POST", request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 댓글 신고 처리
     *
     * @param reportId 처리할 신고의 ID
     * @param request 신고 처리 요청 객체
     * @return 성공 여부 응답
     */
    @PutMapping("/comments/{reportId}")
    public ResponseEntity<ApiResponse<Void>> resolveCommentReport(
            @PathVariable Long reportId,
            @RequestBody UserResolveRequest request
    ) {
        adminReportService.resolveReport(reportId, "COMMENT", request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 사용자 신고 처리
     *
     * @param reportId 처리할 신고의 ID
     * @param request 신고 처리 요청 객체
     * @return 성공 여부 응답
     */
    @PutMapping("/users/{reportId}")
    public ResponseEntity<ApiResponse<Void>> resolveUserReport(
            @PathVariable Long reportId,
            @RequestBody UserResolveRequest request
    ) {
        adminReportService.resolveReport(reportId, "USER", request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
