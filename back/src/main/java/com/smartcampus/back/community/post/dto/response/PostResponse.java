package com.smartcampus.back.community.post.dto.response;

import com.smartcampus.back.community.attachment.dto.AttachmentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 응답 DTO
 * <p>
 * 게시글 제목, 내용, 작성자 정보, 좋아요 수, 댓글 수, 첨부파일 등 전체 정보를 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    /**
     * 게시글 ID
     */
    private Long postId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 본문 내용
     */
    private String content;

    /**
     * 게시글 카테고리
     */
    private String category;

    /**
     * 작성자 ID
     */
    private Long writerId;

    /**
     * 작성자 닉네임
     */
    private String writerNickname;

    /**
     * 작성자 프로필 이미지 URL (nullable)
     */
    private String writerProfileImageUrl;

    /**
     * 작성일시
     */
    private LocalDateTime createdAt;

    /**
     * 마지막 수정일시
     */
    private LocalDateTime updatedAt;

    /**
     * 조회수
     */
    private long viewCount;

    /**
     * 좋아요 수
     */
    private long likeCount;

    /**
     * 댓글 수
     */
    private long commentCount;

    /**
     * 현재 사용자가 좋아요를 눌렀는지 여부
     */
    private boolean likedByMe;

    /**
     * 현재 사용자가 즐겨찾기했는지 여부
     */
    private boolean favoritedByMe;

    /**
     * 첨부파일 목록
     */
    private List<AttachmentResponse> attachments;
}
