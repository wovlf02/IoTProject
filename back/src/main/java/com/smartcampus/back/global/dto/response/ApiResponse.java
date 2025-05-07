package com.smartcampus.back.global.dto.response;

import lombok.Getter;

/**
 * 모든 API 응답의 공통 포맷을 정의하는 클래스입니다.
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
public class ApiResponse<T> {

    /** 응답 성공 여부 */
    private final boolean success;

    /** HTTP 상태 코드 */
    private final int code;

    /** 응답 메시지 */
    private final String message;

    /** 응답 데이터 (Optional) */
    private final T data;

    /**
     * 성공한 응답을 생성합니다.
     *
     * @param data 응답 데이터
     */
    private ApiResponse(T data) {
        this.success = true;
        this.code = 200;
        this.message = "요청이 성공적으로 처리되었습니다.";
        this.data = data;
    }

    /**
     * 실패한 응답을 생성합니다.
     *
     * @param code HTTP 상태 코드
     * @param message 에러 메시지
     */
    private ApiResponse(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = null;
    }

    /**
     * 데이터를 포함한 성공 응답을 생성합니다.
     *
     * @param <T> 데이터 타입
     * @param data 응답 데이터
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    /**
     * 데이터 없이 성공 응답을 생성합니다.
     *
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(null);
    }

    /**
     * 실패 응답을 생성합니다.
     *
     * @param code 상태 코드
     * @param message 메시지
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> failure(int code, String message) {
        return new ApiResponse<>(code, message);
    }
}
