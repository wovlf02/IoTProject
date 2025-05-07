package com.smartcampus.back.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 요청한 리소스를 찾을 수 없을 때 발생하는 예외입니다.
 * 주로 사용자, 게시글, 알림 등이 존재하지 않을 때 사용됩니다.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * 기본 메시지를 사용하는 생성자
     */
    public NotFoundException() {
        super("요청한 리소스를 찾을 수 없습니다.");
    }

    /**
     * 커스텀 메시지를 사용하는 생성자
     *
     * @param message 예외 메시지
     */
    public NotFoundException(String message) {
        super(message);
    }
}
