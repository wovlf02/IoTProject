package com.smartcampus.back.notification.exception;

/**
 * NotificationNotFoundException
 * <p>
 * 요청한 알림이 존재하지 않을 때 발생하는 예외입니다.
 * 주로 알림 조회, 삭제, 읽음 처리 시 유효성 검증에 사용됩니다.
 * </p>
 */
public class NotificationNotFoundException extends RuntimeException {

    /**
     * 기본 생성자
     * <p>
     * 기본 에러 메시지 "Notification not found."를 포함합니다.
     * </p>
     */
    public NotificationNotFoundException() {
        super("Notification not found.");
    }

    /**
     * 상세 메시지를 포함하는 생성자
     *
     * @param message 사용자 정의 상세 메시지
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
