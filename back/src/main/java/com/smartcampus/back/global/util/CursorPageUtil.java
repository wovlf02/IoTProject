package com.smartcampus.back.global.util;

import com.smartcampus.back.global.dto.response.CursorPageResponse;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Cursor 기반 페이지네이션 변환 유틸리티
 * <p>
 * 엔티티 리스트를 CursorPageResponse 형태로 변환합니다.
 * </p>
 */
public class CursorPageUtil {

    /**
     * 엔티티 리스트를 CursorPageResponse로 변환
     *
     * @param entities 변환할 엔티티 리스트
     * @param mapper   엔티티 → DTO 변환 함수
     * @param <T>      엔티티 타입
     * @param <R>      DTO 타입
     * @return 변환된 CursorPageResponse
     */
    public static <T, R> CursorPageResponse<R> convert(List<T> entities, Function<T, R> mapper) {
        // 엔티티를 DTO로 변환
        List<R> content = entities.stream()
                .map(mapper)
                .collect(Collectors.toList());

        // 커서 (마지막 엔티티의 ID)
        Long nextCursor = null;

        if (!entities.isEmpty()) {
            T lastEntity = entities.get(entities.size() - 1);
            nextCursor = extractId(lastEntity);
        }

        // 다음 페이지 여부 결정 (최소 한 개의 데이터가 있으면 다음 페이지 있음)
        boolean hasNext = entities.size() == content.size(); // 해당 페이지에서 한 번에 다 가져왔는지 체크

        return CursorPageResponse.<R>builder()
                .content(content)        // 페이지 내용 (DTO 리스트)
                .cursor(new CursorPageResponse.CursorResult(nextCursor, content.size())) // 커서 정보
                .hasNext(hasNext)        // 더 많은 페이지가 있는지 여부
                .build();
    }

    /**
     * 엔티티에서 ID를 추출하는 메서드
     *
     * @param entity 엔티티 객체
     * @param <T>    엔티티 타입
     * @return 엔티티의 ID 값
     */
    private static <T> Long extractId(T entity) {
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (Long) getIdMethod.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("엔티티에서 ID를 추출할 수 없습니다. getId() 메서드를 확인하세요.", e);
        }
    }
}
