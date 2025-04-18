package com.smartcampus.back.report.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 신고(Report) 엔티티
 * <p>
 * 게시글, 댓글, 대댓글 또는 사용자에 대한 신고 내역을 저장합니다.
 * 하나의 신고는 하나의 사용자(User)가 하나의 대상(Post, Comment, Reply, User)을 신고한 내용으로 구성됩니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "report",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"reporter_id", "post_id", "comment_id", "reply_id", "target_user_id"})
        }
)
public class Report {

    /**
     * 신고 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 신고자 (User)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    /**
     * 신고 대상 게시글 (nullable = true)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 신고 대상 댓글 (nullable = true)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * 신고 대상 대댓글 (nullable = true, Comment로 관리)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Comment reply;

    /**
     * 신고 대상 사용자 (nullable = true)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    /**
     * 신고 유형 (POST, COMMENT, REPLY, USER)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;

    /**
     * 신고 사유
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    /**
     * 신고 처리 상태 (PENDING, RESOLVED, REJECTED 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    /**
     * 신고 생성 시각
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 마지막 상태 변경 시각
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 신고 생성 시 기본 상태 및 시간 설정
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = ReportStatus.PENDING;
    }

    /**
     * 신고 상태 업데이트 시 갱신 시간 변경
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
