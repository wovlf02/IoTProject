package com.smartcampus.back.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 인증되지 않은 사용자가 접근했을 때 발생하는 예외입니다.
 * 주로 JWT 토큰이 없거나 유효하지 않은 경우에 사용됩니다.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /**
     * 기본 메시지를 사용하는 생성자
     */
    public UnauthorizedException() {
        super("인증이 필요한 요청입니다.");
    }

    /**
     * 커스텀 메시지를 사용하는 생성자
     *
     * @param message 예외 메시지
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
