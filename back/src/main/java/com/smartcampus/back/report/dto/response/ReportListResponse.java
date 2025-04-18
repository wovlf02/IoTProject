package com.smartcampus.back.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 관리자 전용 신고 목록 응답 DTO
 * <p>
 * 신고 요약 정보 리스트와 총 개수를 포함합니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportListResponse {

    @Schema(description = "신고 요약 목록")
    private List<ReportSummary> reports;

    @Schema(description = "총 신고 개수", example = "25")
    private int totalCount;

    /**
     * 편의 메서드: 리스트만 받아서 응답 객체 생성
     */
    public static ReportListResponse from(List<ReportSummary> reportList) {
        return ReportListResponse.builder()
                .reports(reportList)
                .totalCount(reportList.size())
                .build();
    }
}
