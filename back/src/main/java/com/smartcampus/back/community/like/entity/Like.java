package com.smartcampus.back.community.like.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Like 엔티티
 * <p>
 * 게시글, 댓글, 대댓글에 대한 좋아요를 저장합니다.
 * 각 좋아요는 하나의 사용자(User)와 하나의 대상(Post, Comment, Reply)와 연결됩니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id", "comment_id", "reply_id"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 좋아요를 누른 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 게시글 좋아요 (nullable: 댓글/대댓글일 경우 null)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 댓글 좋아요 (nullable: 게시글/대댓글일 경우 null)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * 대댓글 좋아요 (nullable: 게시글/댓글일 경우 null)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Comment reply;

    /**
     * 좋아요 누른 시간
     */
    private LocalDateTime likedAt;

    @PrePersist
    public void onCreate() {
        this.likedAt = LocalDateTime.now();
    }
}
