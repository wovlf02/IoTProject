package com.smartcampus.back.report.dto.response;

import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 관리자용 신고 목록에서 사용되는 단일 신고 요약 DTO
 * <p>
 * 간략한 신고 정보 (ID, 타입, 상태, 생성일 등)를 제공합니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportSummary {

    @Schema(description = "신고 ID", example = "101")
    private Long reportId;

    @Schema(description = "신고 대상 ID", example = "52")
    private Long targetId;

    @Schema(description = "신고 대상 타입", example = "POST / COMMENT / REPLY / USER")
    private ReportType type;

    @Schema(description = "신고 처리 상태", example = "PENDING / RESOLVED / REJECTED")
    private ReportStatus status;

    @Schema(description = "신고자 ID", example = "8")
    private Long reporterId;

    @Schema(description = "신고 사유", example = "부적절한 표현 사용")
    private String reason;

    @Schema(description = "신고 접수 일시", example = "2025-04-18T10:22:11")
    private LocalDateTime createdAt;
}
