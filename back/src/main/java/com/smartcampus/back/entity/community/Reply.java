package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 대댓글(Reply) 엔티티
 * - 특정 댓글(Comment)에 대한 대댓글을 나타냅니다.
 * - 작성자(User), 게시글(Post), 댓글(Comment)과 연관됩니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REPLY")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_seq_generator")
    @SequenceGenerator(name = "reply_seq_generator", sequenceName = "REPLY_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 대댓글 내용
     */
    @Column(name = "CONTENT", nullable = false, columnDefinition = "CLOB")
    private String content;

    /**
     * 대댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private User writer;

    /**
     * 소속된 댓글 (상위 댓글)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private Comment comment;

    /**
     * 소속된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    /**
     * 생성 시각
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 수정 시각
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * 좋아요 수 (계산용)
     */
    @Column(name = "LIKE_COUNT", nullable = false)
    private int likeCount;

    /**
     * 좋아요 리스트 (User 기반)
     */
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /**
     * 생성 시 자동으로 날짜 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.likeCount = 0;
    }

    /**
     * 수정 시 자동으로 수정일 설정
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== 비즈니스 로직 =====

    /**
     * 좋아요 수 증가
     */
    public void incrementLikeCount() {
        this.likeCount += 1;
    }

    /**
     * 좋아요 수 감소
     */
    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}