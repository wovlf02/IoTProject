package com.smartcampus.back.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션 전역에서 사용되는 에러 코드 열거형 클래스입니다.
 * <p>
 * 각 에러 코드는 고유한 식별자(code), 사용자 메시지(message), HTTP 상태(httpStatus)를 포함합니다.
 * </p>
 *
 * 사용 예시:
 * <pre>
 *     throw new CustomException(ErrorCode.POST_NOT_FOUND);
 * </pre>
 */
@Getter
public enum ErrorCode {

    // ✅ 공통 예외
    INVALID_INPUT("C001", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("C002", "허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_ERROR("C003", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("C004", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FORBIDDEN("C005", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // ✅ 인증/보안 관련
    UNAUTHORIZED("A001", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("A002", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("A003", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_PASSWORD("A004", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("A005", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USERNAME("A006", "이미 존재하는 아이디입니다.", HttpStatus.CONFLICT),
    DUPLICATE_NICKNAME("A007", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    EMAIL_VERIFICATION_FAILED("A008", "이메일 인증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_CODE_EXPIRED("A009", "인증 코드가 만료되었습니다.", HttpStatus.BAD_REQUEST),

    // ✅ 게시글(Post)
    POST_NOT_FOUND("P001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_ALREADY_DELETED("P002", "이미 삭제된 게시글입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_POST_ACCESS("P003", "게시글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // ✅ 댓글(Comment)
    COMMENT_NOT_FOUND("CM001", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_COMMENT_ACCESS("CM002", "댓글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // ✅ 채팅(Chat)
    CHAT_ROOM_NOT_FOUND("CH001", "채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CHAT_MESSAGE_NOT_FOUND("CH002", "채팅 메시지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // ✅ 첨부파일
    FILE_UPLOAD_FAILED("F001", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_FOUND("F002", "파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FILE_ACCESS_DENIED("F003", "파일 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // ✅ 신고(Report)
    REPORT_ALREADY_EXISTS("R001", "이미 신고한 대상입니다.", HttpStatus.CONFLICT),

    // ✅ 친구(Friend)
    FRIEND_REQUEST_NOT_FOUND("FR001", "친구 요청을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FRIEND_ALREADY_EXISTS("FR002", "이미 친구 상태입니다.", HttpStatus.CONFLICT),
    FRIEND_REQUEST_ALREADY_SENT("FR003", "이미 친구 요청을 보냈습니다.", HttpStatus.CONFLICT),
    FRIEND_BLOCK_CONFLICT("FR004", "차단된 사용자입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
