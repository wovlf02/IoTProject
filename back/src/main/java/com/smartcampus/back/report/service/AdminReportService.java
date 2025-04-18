package com.smartcampus.back.report.service;

import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.report.dto.request.ReportStatusUpdateRequest;
import com.smartcampus.back.report.dto.response.ReportDetailResponse;
import com.smartcampus.back.report.dto.response.ReportListResponse;
import com.smartcampus.back.report.dto.response.ReportSummary;
import com.smartcampus.back.report.entity.Report;
import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.repository.ReportQueryRepository;
import com.smartcampus.back.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 관리자용 신고 처리 서비스 클래스
 * <p>
 * 전체 신고 목록 조회, 상세 조회, 상태 변경 등의 기능을 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class AdminReportService {

    private final ReportRepository reportRepository;
    private final ReportQueryRepository reportQueryRepository;

    /**
     * 전체 신고 목록을 조회합니다.
     *
     * @param type   신고 대상 타입 (POST, COMMENT, REPLY, USER)
     * @param status 신고 처리 상태 (PENDING, RESOLVED 등)
     * @return 필터링된 신고 요약 목록 응답
     */
    @Transactional(readOnly = true)
    public ReportListResponse getAllReports(String type, String status) {
        List<ReportSummary> list = reportQueryRepository.findReportsByTypeAndStatus(type, status);
        return ReportListResponse.builder()
                .reports(list)
                .total(list.size())
                .build();
    }

    /**
     * 단일 신고에 대한 상세 정보를 조회합니다.
     *
     * @param reportId 신고 ID
     * @return 상세 신고 정보 응답 DTO
     */
    @Transactional(readOnly = true)
    public ReportDetailResponse getReportDetail(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return ReportDetailResponse.from(report);
    }

    /**
     * 특정 신고의 상태를 변경합니다.
     *
     * @param reportId 신고 ID
     * @param request  변경할 상태 요청 DTO
     */
    @Transactional
    public void updateReportStatus(Long reportId, ReportStatusUpdateRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        ReportStatus newStatus = ReportStatus.valueOf(request.getStatus());
        report.changeStatus(newStatus);
    }
}
