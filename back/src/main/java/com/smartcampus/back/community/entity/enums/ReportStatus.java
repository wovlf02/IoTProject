package com.smartcampus.back.community.entity.enums;

/**
 * 신고 상태
 * <p>
 * 신고가 처리되지 않은 상태(PENDING) 또는 처리 완료 상태(RESOLVED)
 * </p>
 */
public enum ReportStatus {
    PENDING,    // 미처리 상태
    RESOLVED    // 처리 완료
}
