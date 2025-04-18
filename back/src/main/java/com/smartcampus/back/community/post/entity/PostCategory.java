package com.smartcampus.back.community.post.entity;

/**
 * 게시글 카테고리 열거형
 * <p>
 * 게시글 분류를 위한 Enum 타입입니다.
 * 각 카테고리는 커뮤니티 UI에서 탭 또는 드롭다운으로 제공될 수 있습니다.
 * </p>
 */
public enum PostCategory {

    /**
     * 자유 게시판
     */
    FREE,

    /**
     * 정보 공유 게시판
     */
    INFO,

    /**
     * 질문/답변 게시판
     */
    QUESTION,

    /**
     * 후기 게시판
     */
    REVIEW,

    /**
     * 공지사항 (관리자 전용)
     */
    NOTICE
}
