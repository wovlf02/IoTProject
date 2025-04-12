package com.smartcampus.back.post.enums;

/**
 * 신고 대상의 유형을 정의하는 열거형 ENUM
 */
public enum ReportTargetType {
    POST,
    COMMENT,
    REPLY;

    public static ReportTargetType fromString(String value) {
        try {
            return ReportTargetType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 신고 대상 타입입니다: " + value);
        }
    }
}
