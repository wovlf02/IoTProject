package com.smartcampus.back.post.dto.post;

import com.smartcampus.back.post.dto.attachment.FileDownloadResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 조회 응답 DTO
 * 클라이언트가 특정 게시글을 조회할 때 제공되는 상세 데이터
 */
@Getter
@Builder
public class PostDetailResponse {

    /**
     * 게시글 ID
     */
    private Long postId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 본문
     */
    private String content;

    /**
     * 작성자 ID
     */
    private Long writerId;

    /**
     * 조회수
     */
    private int viewCount;

    /**
     * 공개 여부
     */
    private boolean isPublic;

    /**
     * 생성 시각
     */
    private LocalDateTime createdAt;

    /**
     * 마지막 수정 시각
     */
    private LocalDateTime updatedAt;

    /**
     * 첨부파일 정보 리스트
     */
    private List<FileDownloadResponse> attachments;
}
