package com.smartcampus.back.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * API 응답 공통 포맷
 *
 * 모든 API는 이 클래스를 통해 성공/실패 여부, 메시지, 데이터 또는 에러 정보를 반환합니다.
 *
 * @param <T> 응답 데이터 타입
 */
@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 성공 여부
     */
    private final boolean success;

    /**
     * 에러 코드 또는 성공 코드
     */
    private final String code;

    /**
     * 사용자에게 보여줄 메시지
     */
    private final String message;

    /**
     * 성공 시 반환할 데이터 또는 실패 시 상세 필드 에러 정보
     */
    private final T data;

    /**
     * 요청 성공 응답
     *
     * @param message 사용자 메시지
     * @param data    응답 데이터
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, "200", message, data);
    }

    /**
     * 요청 성공 응답 (기본 메시지: 요청이 성공적으로 처리되었습니다)
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "200", "요청이 성공적으로 처리되었습니다.", data);
    }

    /**
     * 요청 실패 응답
     *
     * @param code    에러 코드
     * @param message 사용자 메시지
     */
    public static ApiResponse<?> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    /**
     * 유효성 검사 실패 시 응답
     *
     * @param code     에러 코드
     * @param message  사용자 메시지
     * @param errorMap 필드별 에러 상세 정보
     */
    public static ApiResponse<Map<String, String>> validationError(String code, String message, Map<String, String> errorMap) {
        return new ApiResponse<>(false, code, message, errorMap);
    }
}
