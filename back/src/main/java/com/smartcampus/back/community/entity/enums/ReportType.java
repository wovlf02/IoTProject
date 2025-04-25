package com.smartcampus.back.community.entity.enums;

/**
 * 신고 대상 유형
 * <p>
 * 게시글, 댓글, 사용자 중 하나를 신고할 수 있음
 * </p>
 */
public enum ReportType {
    POST,       // 게시글 신고
    COMMENT,    // 댓글 신고
    USER        // 사용자 신고
}
