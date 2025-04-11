package com.smartcampus.back.post.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 게시글 검색 조건 DTO
 * 키워드, 작성자, 날짜, 태그 등으로 필터링할 수 있음
 */
@Getter
@Setter
public class PostSearchCondition {

    /**
     * 검색 키워드 (제목, 내용 포함)
     */
    private String keyword;

    /**
     * 작성자 ID 필터 (지정된 작성자의 게시글만 조회)
     */
    private Long writerId;

    /**
     * 공개 게시글만 필터링 여부
     */
    private Boolean isPublic;

    /**
     * 검색 시작일 (작성일 기준)
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    /**
     * 검색 종료일 (작성일 기준)
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
