package com.smartcampus.back.global.util;

import com.smartcampus.back.global.dto.response.CursorPageResponse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 커서 기반 페이지네이션 변환 유틸리티
 *
 * 엔티티 리스트를 CursorPageResponse 형태로 변환합니다.
 */
public class CursorPageUtil {

    /**
     * 엔티티 리스트를 CursorPageResponse로 변환
     *
     * @param entities 변환할 엔티티 리스트 (정렬되어 있어야 함)
     * @param mapper   엔티티 → DTO 변환 함수
     * @param <T>      엔티티 타입
     * @param <R>      DTO 타입
     * @return 변환된 CursorPageResponse
     */
    public static <T, R> CursorPageResponse<R> convert(List<T> entities, Function<T, R> mapper) {
        List<R> content = entities.stream()
                .map(mapper)
                .collect(Collectors.toList());

        Long nextCursor = null;
        if (!entities.isEmpty()) {
            Object last = entities.get(entities.size() - 1);
            try {
                // 기본적으로 엔티티에 getId() 메서드가 있다고 가정
                nextCursor = (Long) last.getClass().getMethod("getId").invoke(last);
            } catch (Exception e) {
                throw new RuntimeException("CursorPageUtil: getId() 메서드가 존재하지 않거나 접근 불가", e);
            }
        }

        return new CursorPageResponse<>(content, nextCursor);
    }
}
