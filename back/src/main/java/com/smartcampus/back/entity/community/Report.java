package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 신고(Report) 엔티티
 * - 사용자, 게시글, 댓글, 대댓글, 다른 사용자를 신고한 내역을 저장합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq_generator")
    @SequenceGenerator(name = "report_seq_generator", sequenceName = "REPORT_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 신고 사유
     */
    @Column(name = "REASON", nullable = false, length = 500)
    private String reason;

    /**
     * 신고 상태 (예: PENDING, RESOLVED 등)
     */
    @Column(name = "STATUS", nullable = false, length = 50)
    private String status;

    /**
     * 신고 생성 날짜
     */
    @Column(name = "REPORTED_AT", nullable = false)
    private LocalDateTime reportedAt;

    /**
     * 신고자 (사용자)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID", nullable = false)
    private User reporter;

    /**
     * 신고 대상: 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    /**
     * 신고 대상: 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    /**
     * 신고 대상: 대댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;

    /**
     * 신고 대상: 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_USER_ID")
    private User targetUser;

    /**
     * 신고 생성 시 시간 초기화
     */
    @PrePersist
    protected void onCreate() {
        this.reportedAt = LocalDateTime.now();
        this.status = "PENDING"; // 기본 상태값 설정
    }
}