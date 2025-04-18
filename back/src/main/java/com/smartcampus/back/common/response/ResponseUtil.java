package com.smartcampus.back.common.response;

import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * API 응답 객체 생성을 도와주는 정적 유틸리티 클래스입니다.
 * ApiResponse 및 PageResponse를 편리하게 생성할 수 있습니다.
 */
public class ResponseUtil {

    /**
     * 일반 성공 응답 (기본 메시지 포함)
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(data);
    }

    /**
     * 일반 성공 응답 (사용자 메시지 포함)
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.success(message, data);
    }

    /**
     * 실패 응답
     */
    public static ApiResponse<?> error(String code, String message) {
        return ApiResponse.error(code, message);
    }

    /**
     * 유효성 검증 실패 응답
     */
    public static ApiResponse<Map<String, String>> validationError(String code, String message, Map<String, String> errorMap) {
        return ApiResponse.validationError(code, message, errorMap);
    }

    /**
     * 페이징 응답 (JPA Page → PageResponse 변환 포함)
     */
    public static <T> ApiResponse<PageResponse<T>> paged(Page<T> page) {
        return ApiResponse.success(PageResponse.from(page));
    }

    /**
     * 페이징 응답 + 메시지 포함
     */
    public static <T> ApiResponse<PageResponse<T>> paged(String message, Page<T> page) {
        return ApiResponse.success(message, PageResponse.from(page));
    }
}
