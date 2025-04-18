package com.smartcampus.back.report.entity;

/**
 * 신고 대상 타입을 구분하는 열거형
 */
public enum ReportType {

    /**
     * 게시글에 대한 신고
     */
    POST,

    /**
     * 댓글에 대한 신고
     */
    COMMENT,

    /**
     * 대댓글에 대한 신고
     */
    REPLY,

    /**
     * 사용자(계정)에 대한 신고
     */
    USER
}
