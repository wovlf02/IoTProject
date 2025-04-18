package com.smartcampus.back.community.comment.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글/대댓글 엔티티
 * <p>
 * 댓글은 게시글(Post)에 속하며, 대댓글은 상위 댓글(Comment)에 종속됩니다.
 * 작성자, 본문, 좋아요 수, 삭제 여부, 차단 여부 등을 포함합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 댓글이 속한 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    /**
     * 댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 상위 댓글 (null이면 최상위 댓글, not null이면 대댓글)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    /**
     * 자식 댓글 (대댓글)
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<Comment> children = new ArrayList<>();

    /**
     * 댓글 본문
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 댓글 작성 시간
     */
    private LocalDateTime createdAt;

    /**
     * 댓글 수정 시간
     */
    private LocalDateTime updatedAt;

    /**
     * 삭제 여부 (Soft delete)
     */
    @Column(nullable = false)
    private boolean deleted;

    /**
     * 좋아요 수 캐싱 (성능 개선 목적)
     */
    @Column(nullable = false)
    private int likeCount;

    /**
     * 댓글 차단 여부 (관리자 차단 시)
     */
    @Column(nullable = false)
    private boolean blocked;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.deleted = false;
        this.likeCount = 0;
        this.blocked = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
