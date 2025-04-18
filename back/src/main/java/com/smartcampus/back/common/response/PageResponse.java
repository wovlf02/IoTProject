package com.smartcampus.back.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 페이징 응답 포맷
 *
 * Spring Data JPA의 Page<T>를 변환하여, API 응답에 맞게 포맷팅합니다.
 *
 * @param <T> 실제 데이터 타입
 */
@Getter
@AllArgsConstructor
public class PageResponse<T> {

    /**
     * 실제 콘텐츠 목록
     */
    private List<T> content;

    /**
     * 현재 페이지 번호 (0부터 시작)
     */
    private int page;

    /**
     * 페이지 크기
     */
    private int size;

    /**
     * 전체 페이지 수
     */
    private int totalPages;

    /**
     * 전체 요소 수
     */
    private long totalElements;

    /**
     * Page<T> 객체를 PageResponse<T>로 변환
     *
     * @param page Page 객체
     * @return PageResponse 객체
     */
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
