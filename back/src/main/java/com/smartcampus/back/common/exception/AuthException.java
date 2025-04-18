package com.smartcampus.back.common.exception;

import lombok.Getter;

/**
 * 인증/인가 과정에서 발생하는 예외를 처리하는 커스텀 예외 클래스입니다.
 * <p>
 * 주로 로그인 실패, 토큰 오류, 접근 권한 부족 등에서 사용됩니다.
 * </p>
 *
 * 사용 예시:
 * <pre>
 *     throw new AuthException(ErrorCode.UNAUTHORIZED);
 * </pre>
 */
@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AuthException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
