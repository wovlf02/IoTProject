package com.smartcampus.back.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 커서 기반 페이지네이션 응답 DTO입니다.
 *
 * @param <T> 응답 객체 타입
 */
@Getter
@AllArgsConstructor
public class CursorPageResponse<T> {

    /** 현재 페이지의 데이터 목록 */
    private final List<T> content;

    /** 다음 페이지를 위한 커서 ID (null이면 마지막 페이지) */
    private final Long nextCursor;

    /**
     * 정적 팩토리 메서드
     *
     * @param content 현재 페이지의 데이터 목록
     * @param <T> 데이터 타입
     * @return CursorPageResponse 객체
     */
    public static <T> CursorPageResponse<T> of(List<T> content) {
        Long nextCursor = content.isEmpty() ? null : extractLastCursor(content);
        return new CursorPageResponse<>(content, nextCursor);
    }

    /**
     * 마지막 요소에서 커서 ID를 추출 (ID 필드가 있는 객체 기준)
     */
    private static <T> Long extractLastCursor(List<T> content) {
        try {
            T last = content.get(content.size() - 1);
            return (Long) last.getClass().getMethod("getMessageId").invoke(last); // getId() or getMessageId() 등 맞게 변경
        } catch (Exception e) {
            return null; // 예외 발생 시 커서 없음 처리
        }
    }
}
