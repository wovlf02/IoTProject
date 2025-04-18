package com.smartcampus.back.report.controller;

import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.report.dto.request.ReportStatusUpdateRequest;
import com.smartcampus.back.report.dto.response.ReportDetailResponse;
import com.smartcampus.back.report.dto.response.ReportListResponse;
import com.smartcampus.back.report.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 전용 신고 관리 API 컨트롤러
 * <p>
 * 전체 신고 목록 조회, 상세 조회, 상태 변경 등의 기능 제공
 * </p>
 */
@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final AdminReportService adminReportService;

    /**
     * 전체 신고 목록 조회 (필터링: 타입, 상태 등)
     *
     * @param type   신고 타입 (POST, COMMENT, REPLY, USER)
     * @param status 신고 상태 (PENDING, RESOLVED 등)
     * @return 필터링된 신고 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<ReportListResponse>> getReportList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status
    ) {
        ReportListResponse response = adminReportService.getReportList(type, status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 신고 상세 정보 조회
     *
     * @param reportId 신고 ID
     * @return 해당 신고의 상세 정보
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<ReportDetailResponse>> getReportDetail(@PathVariable Long reportId) {
        ReportDetailResponse response = adminReportService.getReportDetail(reportId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 신고 처리 상태 업데이트 (승인, 반려 등)
     *
     * @param reportId 신고 ID
     * @param request  처리 상태 변경 요청 DTO
     * @return 처리 완료 메시지
     */
    @PutMapping("/{reportId}/status")
    public ResponseEntity<ApiResponse<String>> updateReportStatus(
            @PathVariable Long reportId,
            @RequestBody ReportStatusUpdateRequest request
    ) {
        adminReportService.updateReportStatus(reportId, request);
        return ResponseEntity.ok(ApiResponse.success("신고 상태가 업데이트되었습니다."));
    }
}
