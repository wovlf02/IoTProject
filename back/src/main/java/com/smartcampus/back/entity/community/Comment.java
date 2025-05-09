package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글(Comment) 엔티티
 * - 게시글(Post)에 작성된 댓글을 나타냅니다.
 * - 사용자(User)에 의해 작성되며, 첨부파일 및 대댓글과 연관됩니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @SequenceGenerator(name = "comment_seq_generator", 
                       sequenceName = "COMMENT_SEQ", 
                       allocationSize = 1)
    private Long id;

    /**
     * 댓글 내용
     */
    @Column(name = "CONTENT", nullable = false, length = 1000)
    private String content;

    /**
     * 댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private User writer;

    /**
     * 댓글이 달린 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    /**
     * 댓글 생성 시각
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 댓글 수정 시각
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * 댓글 좋아요 수
     */
    @Column(name = "LIKE_COUNT", nullable = false)
    private int likeCount;

    /**
     * 댓글 좋아요 리스트
     */
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /**
     * 대댓글 리스트
     */
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    /**
     * 댓글 첨부파일 리스트
     */
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * 댓글 생성 시 시간 초기화
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * 댓글 업데이트 시 시간 갱신
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 댓글 내용 수정
     */
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    /**
     * 좋아요 수 증가
     */
    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    /**
     * 좋아요 수 감소
     */
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}