package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * CommentReply 엔티티 클래스
 *
 * 게시글의 댓글(PostComment)에 대한 대댓글을 저장하는 테이블 (comment_replies)과 매핑
 * 대댓글 작성자(User), 부모 댓글(PostComment)와의 관계 설정
 * 대댓글 작성 시각 포함
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
     * 대댓글 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 대댓글을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    /**
     * 부모 댓글 ID (Foreign Key)
     *
     * PostComment 테이블의 comment_id(post_comments.comment_id)와 연결
     * ManyToOne 관계 설정 (하나의 댓글에 여러 개의 대댓글이 존재 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REPLY_COMMENT"))
    private PostComment comment;

    /**
     * 대댓글 작성자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 개의 대댓글 작성 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REPLY_USER"))
    private User user;

    /**
     * 대댓글 내용
     *
     * 최대 500자까지 저장 가능
     * 공백 불가능 (null 허용 X)
     */
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    /**
     * 대댓글 작성 시간 (자동 설정)
     *
     * 사용자가 대댓글을 작성한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
