package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * CommentReply 엔티티 클래스
 * comment_replies 테이블과 매핑됨
 * 특정 댓글(PostComment)에 대한 대댓글을 저장하는 엔티ㅣ
 * 사용자(User)와 댓글(PostComment)의 다대일(Many-to-One) 관계를 설정
 * 부모 댓글이 삭제되면 해당 대댓글도 함께 삭제됨 (CascadeType.ALL)
 */
@Entity
@Table(name = "comment_replies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReply {

    /**
     * 대댓글 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    /**
     * 부모 댓글 ID (PostComment)
     * post_comments 테이블과 FK 관계 (Many-to-One)
     * 부모 댓글이 삭제되면 대댓글도 함께 삭제됨
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", nullable = false)
    private PostComment comment;

    /**
     * 대댓글 작성자 ID
     * users 테이블과 FK 관계 (Many-to-One)
     * 사용자가 삭제되더라도 대댓글은 유지됨
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 대댓글 내용
     * 최대 500자 제한
     * Null 불가 (Not Null)
     */
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    /**
     * 대댓글 작성 시각
     * Default: 현재 시각
     * 사용자가 대댓글을 작성한 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
