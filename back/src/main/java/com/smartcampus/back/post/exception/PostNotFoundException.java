package com.smartcampus.back.post.exception;

/**
 * 게시글이 존재하지 않을 때 발생하는 예외
 */
public class PostNotFoundException extends RuntimeException {

    /**
     * 기본 생성자
     */
    public PostNotFoundException() {
        super("해당 게시글을 찾을 수 없습니다.");
    }

    /**
     * 사용자 정의 메시지 포함 생성자
     * 
     * @param message 사용자 정의 에러 메시지
     */
    public PostNotFoundException(String message) {
        super(message);
    }
}
