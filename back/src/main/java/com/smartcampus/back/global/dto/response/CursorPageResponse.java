package com.smartcampus.back.global.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * CursorPageResponse
 * <p>
 * 커서 기반 페이지 응답을 위한 DTO입니다. 페이징된 데이터와 커서 정보를 함께 반환합니다.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class CursorPageResponse<T> {

    private final List<T> data;      // 페이징된 데이터 리스트
    private final CursorResult cursor;  // 커서 정보 (다음 페이지 커서, 페이지 크기)

    /**
     * 커서 기반 페이징 응답 객체를 생성하는 메서드
     *
     * @param data 페이징된 데이터
     * @param cursor 커서 정보 (다음 페이지 커서, 페이지 크기)
     * @param <T> 데이터 타입
     * @return 페이징된 데이터와 커서를 포함한 응답 객체
     */
    public static <T> CursorPageResponse<T> of(List<T> data, CursorResult cursor) {
        return new CursorPageResponse<>(data, cursor);
    }

    /**
     * 커서 결과 객체 (다음 페이지 커서, 페이지 크기)
     */
    @Getter
    @RequiredArgsConstructor
    public static class CursorResult {

        private final Long nextCursor;  // 다음 페이지 커서 (null이면 마지막 페이지)
        private final int size;         // 페이지 크기 (몇 개의 아이템을 반환할지)

        /**
         * 커서 정보 객체를 생성하는 메서드
         *
         * @param nextCursor 다음 페이지 커서 (null이면 마지막 페이지)
         * @param size 페이지 크기
         * @return 커서 결과 객체
         */
        public static CursorResult of(Long nextCursor, int size) {
            return new CursorResult(nextCursor, size);
        }
    }
}
