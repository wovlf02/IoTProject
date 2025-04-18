package com.smartcampus.back.common.exception;

import com.smartcampus.back.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러
 * <p>
 * CustomException, AuthException, RuntimeException 등 모든 예외를 처리하여
 * 일관된 JSON 응답 형식을 제공합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 기반 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        log.warn("CustomException: {}", e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.error(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    /**
     * 인증/인가 관련 예외 처리
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthException(AuthException e) {
        log.warn("AuthException: {}", e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.error(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    /**
     * @Valid 외부 검증기용 (예: PathVariable, RequestParam 유효성 위반 등)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException e) {
        log.warn("ConstraintViolationException: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        ErrorCode.INVALID_INPUT.getCode(),
                        "유효하지 않은 요청 값입니다."
                ));
    }

    /**
     * 예상하지 못한 모든 런타임 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        log.error("Unhandled RuntimeException", e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getHttpStatus())
                .body(ApiResponse.error(
                        ErrorCode.INTERNAL_ERROR.getCode(),
                        "알 수 없는 오류가 발생했습니다."
                ));
    }
}
