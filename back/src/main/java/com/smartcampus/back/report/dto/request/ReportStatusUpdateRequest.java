package com.smartcampus.back.report.dto.request;

import com.smartcampus.back.report.entity.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 관리자용 신고 상태 변경 요청 DTO
 * <p>
 * 관리자가 특정 신고 건에 대해 상태(PENDING, RESOLVED, REJECTED 등)를 변경할 때 사용합니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportStatusUpdateRequest {

    @Schema(description = "변경할 신고 상태", example = "RESOLVED")
    @NotNull(message = "신고 상태는 필수입니다.")
    private ReportStatus status;
}
