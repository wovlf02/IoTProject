package com.smartcampus.back.common.exception;

import com.smartcampus.back.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO에 대한 @Valid 유효성 검증 실패 처리 전용 핸들러입니다.
 * 필드별 에러 메시지를 클라이언트에게 JSON 형태로 반환합니다.
 */
@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * @RequestBody DTO에서 @Valid 검증 실패 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.validationError(
                        ErrorCode.INVALID_INPUT.getCode(),
                        "입력값이 유효하지 않습니다.",
                        errorMap
                ));
    }

    /**
     * @RequestParam, @PathVariable 등의 단일 값 유효성 검증 실패 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        ErrorCode.INVALID_INPUT.getCode(),
                        "요청 파라미터가 유효하지 않습니다."
                ));
    }
}
