package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 좋아요(Like) 엔티티
 * - 게시글, 댓글, 대댓글에 대해 사용자가 좋아요를 표시한 기록을 저장합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "LIKE",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_LIKE_USER_POST", columnNames = {"USER_ID", "POST_ID"}),
                @UniqueConstraint(name = "UK_LIKE_USER_COMMENT", columnNames = {"USER_ID", "COMMENT_ID"}),
                @UniqueConstraint(name = "UK_LIKE_USER_REPLY", columnNames = {"USER_ID", "REPLY_ID"})
        }
)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq_generator")
    @SequenceGenerator(name = "like_seq_generator", sequenceName = "LIKE_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 좋아요를 누른 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * 좋아요가 눌린 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    /**
     * 좋아요가 눌린 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    /**
     * 좋아요가 눌린 대댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;
}