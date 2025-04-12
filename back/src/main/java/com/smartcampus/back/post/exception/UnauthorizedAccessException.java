package com.smartcampus.back.post.exception;

/**
 * 사용자가 허용되지 않은 자원에 접근하려 할 때 발생하는 예외입니다.
 * 예: 게시글 수정, 삭제 등 본인이 작성하지 않은 콘텐츠에 대한 접근
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     * 메시지를 포함한 예외 생성자
     *
     * @param message 사용자에게 보여줄 예외 메시지
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인 예외를 포함한 생성자
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
