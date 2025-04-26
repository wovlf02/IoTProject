package com.smartcampus.back.community.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.entity.enums.ReportStatus;
import com.smartcampus.back.community.entity.enums.ReportType;
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
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "reporter_id", "post_id", "comment_id", "reply_id", "target_user_id"
        })
)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 신고한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    /**
     * 신고 대상 게시글 ID (nullable: 댓글/유저 신고일 경우)
     */
    @Column(name = "post_id")
    private Long postId;

    /**
     * 신고 대상 댓글 ID (nullable)
     */
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * 신고 대상 대댓글 ID (nullable)
     */
    @Column(name = "reply_id")
    private Long replyId;

    /**
     * 신고 대상 사용자 ID (nullable: 게시글/댓글 신고일 경우)
     */
    @Column(name = "target_user_id")
    private Long targetUserId;

    /**
     * 신고 유형 (게시글 / 댓글 / 사용자 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

    /**
     * 신고 사유 (비속어 사용, 부적절한 내용 등)
     */
    @Column(nullable = false)
    private String reason;

    /**
     * 신고 상태 (PENDING, RESOLVED)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    /**
     * 관리자 처리 조치 (예: 숨김, 삭제, 정지 등)
     */
    private String action;

    /**
     * 관리자 처리 메모
     */
    private String memo;

    /**
     * 신고 생성일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 신고 처리일시
     */
    private LocalDateTime resolvedAt;

    /**
     * 신고 처리 여부
     */
    public boolean isResolved() {
        return this.status == ReportStatus.RESOLVED;
    }

    /**
     * 신고 처리 수행 메서드
     *
     * @param action 관리자 조치 유형 (예: HIDE, DELETE, SUSPEND)
     * @param memo 처리 메모
     */
    public void resolve(String action, String memo) {
        this.status = ReportStatus.RESOLVED;
        this.action = action;
        this.memo = memo;
        this.resolvedAt = LocalDateTime.now();
    }

    /**
     * 생성 시각 자동 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = ReportStatus.PENDING;
    }
}
