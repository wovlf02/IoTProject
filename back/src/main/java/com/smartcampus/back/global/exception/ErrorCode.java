package com.smartcampus.back.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 에러 코드 정의
 * <p>
 * SmartCampus 앱 전용 에러 코드 및 기본 메시지를 관리합니다.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통 오류
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE("잘못된 입력값입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),
    UNAUTHORIZED("인증이 필요합니다."),

    // 인증/회원 오류
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    DUPLICATE_USERNAME("이미 사용 중인 아이디입니다."),
    DUPLICATE_NICKNAME("이미 사용 중인 닉네임입니다."),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_VERIFICATION_CODE("인증코드가 유효하지 않습니다."),
    EMAIL_SEND_FAILED("이메일 전송에 실패했습니다."),

    INVALID_EMAIL_CODE("잘못된 이메일 인증 코드입니다."),
    EMAIL_NOT_MATCH("아이디와 이메일이 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다."),

    // 커뮤니티 오류
    POST_NOT_FOUND("게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    REPLY_NOT_FOUND("대댓글을 찾을 수 없습니다."),
    ALREADY_BLOCKED("이미 차단된 대상입니다."),
    ALREADY_LIKED("이미 좋아요를 누른 상태입니다."),
    REPORT_NOT_FOUND("신고 내역을 찾을 수 없습니다."),

    // 채팅 오류
    CHAT_ROOM_NOT_FOUND("채팅방을 찾을 수 없습니다."),
    MESSAGE_SEND_FAILED("메시지 전송에 실패했습니다."),

    // 시간표/네비게이션 오류
    SCHEDULE_NOT_FOUND("시간표 정보를 찾을 수 없습니다."),
    LOCATION_NOT_FOUND("위치 정보를 찾을 수 없습니다."),
    NAVIGATION_FAILED("길찾기 정보를 가져오는데 실패했습니다."),

    // 알림 오류
    FCM_SEND_FAILED("푸시 알림 전송에 실패했습니다."),
    NOTIFICATION_SETTING_NOT_FOUND("알림 설정 정보를 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND("알림을 찾을 수 없습니다.");

    private final String message;
}
