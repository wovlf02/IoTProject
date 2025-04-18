package com.smartcampus.back.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 신고 요청 DTO
 * <p>
 * 사용자가 게시글, 댓글, 대댓글, 또는 사용자를 신고할 때 사용되는 요청 본문입니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequest {

    @Schema(description = "신고 사유", example = "부적절한 언어 사용")
    @NotBlank(message = "신고 사유는 필수입니다.")
    private String reason;
}
