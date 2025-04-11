package com.smartcampus.back.post.dto.report;

import lombok.Builder;
import lombok.Getter;

/**
 * 신고 처리 결과 응답 DTO
 * 신고가 정상적으로 접수되었는지 여부를 응답
 */
@Getter
@Builder
public class ReportResponse {

    /**
     * 신고 처리 결과 메시지
     */
    private String message;

    /**
     * 신고된 대상의 ID (postId, commentId, replyId 등)
     */
    private Long targetId;

    /**
     * 신고된 대상의 타입 (POST, COMMENT, REPLY)
     */
    private String targetType;
}
