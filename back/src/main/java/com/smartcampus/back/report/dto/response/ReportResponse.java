package com.smartcampus.back.report.dto.response;

import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자 신고 처리 결과 응답 DTO
 * <p>
 * 사용자가 신고를 보냈을 때 서버에서 응답하는 신고 처리 결과입니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {

    @Schema(description = "신고 ID", example = "102")
    private Long reportId;

    @Schema(description = "신고 대상 ID", example = "45")
    private Long targetId;

    @Schema(description = "신고 대상 타입", example = "POST / COMMENT / REPLY / USER")
    private ReportType type;

    @Schema(description = "신고 상태", example = "PENDING")
    private ReportStatus status;

    @Schema(description = "신고 접수 시각", example = "2025-04-18T14:33:21")
    private LocalDateTime createdAt;

    @Schema(description = "처리 결과 메시지", example = "신고가 정상적으로 접수되었습니다.")
    private String message;
}
