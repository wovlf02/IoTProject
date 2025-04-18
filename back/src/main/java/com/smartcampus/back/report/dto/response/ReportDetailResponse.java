package com.smartcampus.back.report.dto.response;

import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 관리자 전용 신고 상세 조회 응답 DTO
 * <p>
 * 특정 신고 건의 상세 내용을 관리자에게 제공하는 용도입니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDetailResponse {

    @Schema(description = "신고 ID", example = "101")
    private Long reportId;

    @Schema(description = "신고 대상 ID (게시글/댓글/대댓글/사용자)", example = "55")
    private Long targetId;

    @Schema(description = "신고 대상 타입", example = "POST / COMMENT / REPLY / USER")
    private ReportType type;

    @Schema(description = "신고한 사용자 ID", example = "12")
    private Long reporterId;

    @Schema(description = "신고 사유", example = "욕설 및 부적절한 언어 사용")
    private String reason;

    @Schema(description = "신고 처리 상태", example = "PENDING / RESOLVED / REJECTED")
    private ReportStatus status;

    @Schema(description = "신고 접수 일시", example = "2024-03-10T11:23:45")
    private LocalDateTime createdAt;

    @Schema(description = "마지막 상태 변경 일시", example = "2024-03-12T14:05:00")
    private LocalDateTime updatedAt;
}
