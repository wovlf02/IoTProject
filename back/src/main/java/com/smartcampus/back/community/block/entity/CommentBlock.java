package com.smartcampus.back.community.block.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 댓글 차단 엔티티
 *
 * <p>
 * 사용자가 특정 댓글을 피드에서 차단한 내역을 저장합니다.
 * 각 차단은 (user, comment) 조합으로 유일합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "comment_block",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"})
)
public class CommentBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 차단한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 차단된 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    /**
     * 차단 일시
     */
    private LocalDateTime blockedAt;

    @PrePersist
    protected void onCreate() {
        this.blockedAt = LocalDateTime.now();
    }
}
