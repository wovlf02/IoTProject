package com.smartcampus.back.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 중복된 값(아이디, 닉네임, 이메일 등)으로 인해 요청을 처리할 수 없을 때 발생하는 예외입니다.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedException extends RuntimeException {

    /**
     * 기본 메시지를 사용하는 생성자
     */
    public DuplicatedException() {
        super("중복된 값이 존재합니다.");
    }

    /**
     * 커스텀 메시지를 사용하는 생성자
     *
     * @param message 예외 메시지
     */
    public DuplicatedException(String message) {
        super(message);
    }
}
