package com.smartcampus.back.dto.community.post.response;

import com.smartcampus.back.entity.community.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 게시글 요약 응답 DTO (목록 조회용)
 */
@Data
@Builder
@AllArgsConstructor
public class PostSimpleResponse {

    private Long postId;
    private String title;
    private String category;
    private String writerNickname;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private boolean liked;
    private boolean favorite;
    private LocalDateTime createdAt;

    /** ✅ 본문 내용 (요약용) */
    private String content;

    /** ✅ 첨부파일 개수 */
    private int imageCount;

    /**
     * Post 엔티티를 DTO로 변환
     */
    public static PostSimpleResponse from(Post post) {
        return PostSimpleResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .writerNickname(post.getWriter().getNickname())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .liked(false)
                .favorite(false)
                .createdAt(post.getCreatedAt())
                .content(post.getContent()) // ✅ 추가된 필드
                .imageCount(post.getAttachments() != null ? post.getAttachments().size() : 0) // ✅ 추가된 필드
                .build();
    }
}
