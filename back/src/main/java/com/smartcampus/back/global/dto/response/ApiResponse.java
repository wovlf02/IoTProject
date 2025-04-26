package com.smartcampus.back.global.dto.response;

import lombok.Getter;

/**
 * API 응답 표준 포맷
 * <p>
 * 모든 API 응답은 ApiResponse로 감싸서 반환됩니다.
 * </p>
 *
 * @param <T> 실제 데이터 타입
 */
@Getter
public class ApiResponse<T> {

    /**
     * 요청 처리 결과 (성공 여부)
     */
    private final boolean success;

    /**
     * 실제 응답 데이터 (없을 수도 있음)
     */
    private final T data;

    /**
     * 실패 시 에러 메시지 (성공 시 null)
     */
    private final String error;

    /**
     * 성공 응답 생성
     *
     * @param data 반환할 데이터
     * @return 성공 ApiResponse 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    /**
     * 성공 (데이터 없이) 응답 생성
     *
     * @return 성공 ApiResponse 객체 (data = null)
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, null, null);
    }

    /**
     * 실패 응답 생성
     *
     * @param errorMessage 에러 메시지
     * @return 실패 ApiResponse 객체
     */
    public static <T> ApiResponse<T> failure(String errorMessage) {
        return new ApiResponse<>(false, null, errorMessage);
    }

    // private 생성자 (직접 생성 방지)
    private ApiResponse(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
