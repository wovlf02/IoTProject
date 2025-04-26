package com.smartcampus.back.auth.exception;

import com.smartcampus.back.global.exception.ErrorCode;
import lombok.Getter;

/**
 * AuthException
 * <p>
 * 인증(Authentication) 및 인가(Authorization) 과정에서 발생하는 예외를 처리하는 커스텀 예외 클래스입니다.
 * </p>
 */
@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * 기본 생성자
     *
     * @param errorCode 발생한 에러 코드
     */
    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
