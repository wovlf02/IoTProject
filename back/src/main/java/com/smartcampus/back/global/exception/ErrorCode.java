package com.smartcampus.back.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * [ErrorCode]
 *
 * 전역 예외 처리에서 사용할 에러 코드 Enum 클래스.
 * 각 예외 상황별로 고유한 에러 코드, 메시지, HTTP 상태 코드를 정의함.
 *
 * 프론트엔드에서 error.code를 기반으로 사용자 정의 처리를 하거나
 * 국제화된 메시지를 출력할 수 있도록 설계됨.
 *
 * 사용 예시:
 * throw new CustomException(ErrorCode.USER_NOT_FOUND);
 */
@Getter
public enum ErrorCode {

    LOGIN_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "E1001", "존재하지 않는 사용자입니다."),
    LOGIN_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "E1002", "비밀번호가 일치하지 않습니다."),


    // 400 Bad Request
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "E4001", "잘못된 요청입니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "E4002", "필수 파라미터가 누락되었습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "E4003", "파일 업로드에 실패했습니다."),

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E4011", "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E4012", "토큰이 유효하지 않습니다."),

    // 403 Forbidden
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "E4031", "접근 권한이 없습니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E4041", "사용자를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "E4042", "채팅방을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "E4043", "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "E4044", "댓글을 찾을 수 없습니다."),

    // 409 Conflict
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "E4091", "이미 존재하는 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "E4092", "이미 존재하는 닉네임입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "E4093", "이미 등록된 이메일입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E5001", "서버 내부 오류가 발생했습니다.");

    /**
     * HTTP 상태 코드 (예: 400, 404, 500 등)
     */
    private final HttpStatus httpStatus;

    /**
     * 에러 코드 (프론트엔드 전용 식별자)
     * 예: E4001, E4042 등
     */
    private final String code;

    /**
     * 사용자에게 노출될 에러 메시지
     */
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
