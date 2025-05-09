package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글(Post) 엔티티
 * - 커뮤니티 게시글을 나타냅니다.
 * - 작성자(User)에 의해 생성되며, 댓글, 첨부파일, 좋아요, 신고와 연관됩니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_generator")
    @SequenceGenerator(name = "post_seq_generator", sequenceName = "POST_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 게시글 제목
     */
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    /**
     * 게시글 내용
     */
    @Column(name = "CONTENT", nullable = false, columnDefinition = "CLOB")
    private String content;

    /**
     * 카테고리
     */
    @Column(name = "CATEGORY", nullable = false, length = 50)
    private String category;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private User writer;

    /**
     * 생성 날짜
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 수정 날짜
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * 좋아요 수
     */
    @Column(name = "LIKE_COUNT", nullable = false)
    private int likeCount;

    /**
     * 조회 수
     */
    @Column(name = "VIEW_COUNT", nullable = false)
    private int viewCount;

    /**
     * 댓글 수
     */
    @Column(name = "COMMENT_COUNT", nullable = false)
    private int commentCount;

    /**
     * 댓글
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 첨부파일
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * 좋아요
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /**
     * 게시글 생성 시 날짜 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.likeCount = 0;
        this.viewCount = 0;
        this.commentCount = 0;
    }

    /**
     * 게시글 수정 시 날짜 업데이트
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== 비즈니스 로직 =====

    /**
     * 게시글 조회수 증가
     */
    public void incrementViewCount() {
        this.viewCount += 1;
    }

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

    /**
     * 댓글 수 증가
     */
    public void incrementCommentCount() {
        this.commentCount += 1;
    }

    /**
     * 댓글 수 감소
     */
    public void decrementCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount -= 1;
        }
    }
}