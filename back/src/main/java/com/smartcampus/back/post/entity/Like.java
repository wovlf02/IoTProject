package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 게시글, 댓글, 대댓글에 대한 '좋아요'를 표현하는 엔티티입니다.
 * 복합 키(사용자 + 대상(post/comment/reply))로 구성되며, 하나의 Like는 하나의 대상에만 연결됩니다.
 */
@Entity
@Table(name = "post_likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    /**
     * 복합 키 (userId + postId/commentId/replyId)
     */
    @EmbeddedId
    private LikeId id;

    /**
     * 게시글 좋아요 대상 (nullable)
     * 복합 키의 postId와 매핑됩니다.
     */
    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 댓글 좋아요 대상 (nullable)
     * 복합 키의 commentId와 매핑됩니다.
     */
    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * 대댓글 좋아요 대상 (nullable)
     * 복합 키의 replyId와 매핑됩니다.
     */
    @MapsId("replyId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    /**
     * 좋아요 생성 시각
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
