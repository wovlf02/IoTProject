package com.smartcampus.back.post.exception;

/**
 * 알림이 존재하지 않을 때 발생하는 예외입니다.
 * 주로 알림 ID로 조회 실패 시 사용됩니다.
 */
public class NotificationNotFoundException extends RuntimeException {

    /**
     * 기본 생성자 - 메시지 없이 예외 발생
     */
    public NotificationNotFoundException() {
        super("해당 알림을 찾을 수 없습니다.");
    }

    /**
     * 사용자 정의 메시지를 포함한 생성자
     *
     * @param message 예외 메시지
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
