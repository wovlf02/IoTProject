package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * PostComment 엔티티 클래스
 * post_comments 테이블과 매핑됨
 * 게시글(Post)에 사용자가 작성한 댓글을 저장하는 엔티티
 * 사용자(User)와 게시글(Post)의 다대일(Many-to-One) 관계를 설정
 * 게시글이 삭제되면 해당 게시글의 댓글도 함께 삭제됨 (CascadeType.ALL)
 */
@Entity
@Table(name = "post_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment {

    /**
     * 댓글 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    /**
     * 댓글이 자성된 게시글 ID
     * posts 테이블과 FK 관계 (Many-to-One)
     * 게시글이 삭제되면 댓글도 함께 삭제됨
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * 댓글 작성자 ID
     * users 테이블과 FK 관계 (Many-to-One)
     * 사용자가 삭제되더라도 댓글은 유지됨
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 댓글 내용
     * 최대 500자 제한
     * Null 불가 (Not Null)
     */
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    /**
     * 댓글 작성 시각
     * Default: 현재 시각
     * 사용자가 댓글을 작성한 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
