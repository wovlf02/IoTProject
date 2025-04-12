package com.smartcampus.back.post.dto.report;

import com.smartcampus.back.post.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글/댓글/대댓글 신고 요청 DTO
 * 사용자가 신고할 때 신고 유형과 선택 사유를 포함
 */
@Getter
@Setter
public class ReportRequest {

    /**
     * 신고자 사용자 ID
     */
    @NotNull(message = "신고자 ID는 필수입니다.")
    private Long reporterId;

    /**
     * 신고 유형 (SPAM, ABUSE, OTHER 등)
     */
    @NotNull(message = "신고 유형은 필수입니다.")
    private ReportType type;

    /**
     * 선택 입력: 신고 상세 사유 (자유 텍스트)
     */
    private String reason;
}
