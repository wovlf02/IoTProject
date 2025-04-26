package com.smartcampus.back.global.exception;

import lombok.Getter;

/**
 * 사용자 정의 예외 (CustomException)
 * <p>
 * SmartCampus 앱의 모든 비즈니스 예외를 담당합니다.
 * </p>
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * 에러코드만으로 예외를 생성할 때 사용
     *
     * @param errorCode 에러 코드
     */
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 에러코드와 커스텀 메시지로 예외를 생성할 때 사용
     *
     * @param errorCode 에러 코드
     * @param message 커스텀 에러 메시지
     */
    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
