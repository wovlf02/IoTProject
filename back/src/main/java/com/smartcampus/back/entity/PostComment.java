package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * PostComment 엔티티 클래스
 *
 * 사용자가 게시글(Post)에 남긴 댓글을 저장하는 테이블(post_comments)과 매핑
 * 댓글 작성자(User)와 게시글(Post)과의 관계 설정
 * 댓글 작성 시각 포함
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
     * 댓글 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 댓글을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * 게시글 ID (Foreign Key)
     *
     * Post 테이블의 post_id(posts.post_id)와 연결
     * ManyToOne 관계 설정 (하나의 게시글에 여러 개의 댓글이 달릴 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMMENT_POST"))
    private Post post;

    /**
     * 댓글 작성자 ID (Primary Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 개의 댓글을 작성 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMMENT_USER"))
    private User user;

    /**
     * 댓글 내용
     *
     * 최대 500자까지 저장 가능
     * 공백 불가 (null 허용 X)
     */
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    /**
     * 댓글 작성 시간 (자동 설정)
     *
     * 사용자가 댓글을 작성한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     * 수정 기능이 없는 경우, updateTimestamp가 필요 없음
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
