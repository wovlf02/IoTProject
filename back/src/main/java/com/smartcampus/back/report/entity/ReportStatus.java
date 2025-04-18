package com.smartcampus.back.report.entity;

/**
 * 신고 처리 상태를 나타내는 열거형
 */
public enum ReportStatus {

    /**
     * 신고가 접수된 상태 (기본값)
     */
    PENDING,

    /**
     * 신고가 처리되어 조치가 완료된 상태
     */
    RESOLVED,

    /**
     * 신고가 반려된 상태 (허위, 사유 부족 등)
     */
    REJECTED
}
