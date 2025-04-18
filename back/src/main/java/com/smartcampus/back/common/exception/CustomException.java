package com.smartcampus.back.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 커스텀 예외 클래스입니다.
 * <p>
 * 에러 코드를 통해 일관된 응답 형식을 유지하고,
 * 클라이언트가 처리 가능한 메시지를 전달할 수 있도록 설계되었습니다.
 * </p>
 *
 * 사용 예시:
 * <pre>
 *     throw new CustomException(ErrorCode.USER_NOT_FOUND);
 * </pre>
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
