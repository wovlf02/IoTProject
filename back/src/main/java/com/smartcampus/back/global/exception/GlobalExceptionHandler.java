package com.smartcampus.back.global.exception;

import com.smartcampus.back.global.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역(Global) 예외 처리 핸들러
 * <p>
 * 모든 Controller에서 발생하는 예외를 일관된 포맷으로 처리합니다.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CustomException 처리
     *
     * @param ex 사용자 정의 예외
     * @return 표준 ApiResponse 형태로 에러 응답
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
        log.warn("[CustomException] {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(ex.getMessage()));
    }

    /**
     * @Valid 검증 실패 처리 (MethodArgumentNotValidException)
     *
     * @param ex 검증 실패 예외
     * @return 가장 첫 번째 오류 메시지 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "잘못된 요청입니다.";
        log.warn("[ValidationException] {}", errorMessage);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorMessage));
    }

    /**
     * 그 외 모든 예외 처리
     *
     * @param ex 처리되지 않은 예외
     * @return 서버 내부 오류 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("[Exception] 서버 내부 오류", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
