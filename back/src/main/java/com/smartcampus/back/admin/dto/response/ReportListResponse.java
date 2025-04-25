package com.smartcampus.back.admin.dto.response;

import com.smartcampus.back.community.entity.Report;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 신고 목록 응답 DTO
 * <p>
 * 신고 항목 리스트 + 페이징 정보 포함
 * </p>
 */
@Getter
@Builder
public class ReportListResponse {

    private List<ReportSummary> reports;  // 신고 목록
    private int page;                     // 현재 페이지
    private int size;                     // 페이지 크기
    private long totalElements;           // 전체 신고 수
    private int totalPages;               // 전체 페이지 수

    /**
     * Page<Report> 객체를 기반으로 DTO 변환
     */
    public static ReportListResponse fromPage(Page<Report> page) {
        return ReportListResponse.builder()
                .reports(page.getContent().stream()
                        .map(ReportSummary::fromEntity)
                        .collect(Collectors.toList()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * 신고 목록 개별 요약 DTO
     */
    @Getter
    @Builder
    public static class ReportSummary {
        private Long reportId;
        private String reportType;
        private String reason;
        private String reporterUsername;
        private Long postId;
        private Long commentId;
        private Long targetUserId;
        private String status;
        private String createdAt;

        public static ReportSummary fromEntity(Report report) {
            return ReportSummary.builder()
                    .reportId(report.getId())
                    .reportType(report.getReportType().name())
                    .reason(report.getReason())
                    .reporterUsername(report.getReporter().getUsername())
                    .postId(report.getPostId())
                    .commentId(report.getCommentId())
                    .targetUserId(report.getTargetUserId())
                    .status(report.getStatus().name())
                    .createdAt(report.getCreatedAt().toString())
                    .build();
        }
    }
}
