package com.smartcampus.back.post.exception;

/**
 * 대댓글(답글)이 존재하지 않을 때 발생하는 예외
 */
public class ReplyNotFoundException extends RuntimeException{

    /**
     * 기본 생성자
     */
    public ReplyNotFoundException() {
        super("해당 대댓글을 찾을 수 없습니다.");
    }

    /**
     * 사용자 정의 메시지 포함 생성자
     *
     * @param message 사용자 정의 메시지
     */
    public ReplyNotFoundException(String message) {
        super(message);
    }
}
