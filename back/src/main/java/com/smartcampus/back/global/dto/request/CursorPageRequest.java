package com.smartcampus.back.global.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CursorPageRequest
 * <p>
 * Cursor 기반 무한 스크롤 조회 시 사용하는 요청 객체입니다.
 * 마지막 조회된 cursor ID와 요청할 데이터 수(size)를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class CursorPageRequest {

    /**
     * 마지막으로 조회한 데이터의 ID (nullable)
     */
    private Long cursor;

    /**
     * 한 번에 조회할 데이터 수
     */
    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    private int size = 10; // 기본값 10개 조회

    /**
     * 생성자
     *
     * @param cursor 마지막 데이터 ID
     * @param size 조회할 데이터 수
     */
    public CursorPageRequest(Long cursor, int size) {
        this.cursor = cursor;
        this.size = size;
    }
}
