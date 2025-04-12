package com.smartcampus.back.post.dto.report;

import com.smartcampus.back.post.enums.ReportType;
import lombok.Builder;
import lombok.Getter;

/**
 * 신고 처리 결과 응답 DTO
 */
@Getter
@Builder
public class ReportResponse {

    /**
     * 신고 고유 ID
     */
    private Long reportId;

    /**
     * 신고된 대상의 ID
     */
    private Long targetId;

    /**
     * 신고된 대상의 타입 (POST, COMMENT, REPLY)
     */
    private String targetType;

    /**
     * 신고 유형 (SPAM, ABUSE, OTHER 등)
     */
    private ReportType reportType;

    /**
     * 사용자에게 보여줄 처리 결과 메시지
     */
    private String message;
}
