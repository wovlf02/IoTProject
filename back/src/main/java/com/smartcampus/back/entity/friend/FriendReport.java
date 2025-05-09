package com.smartcampus.back.entity.friend;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자 신고(FriendReport) 엔티티
 * - 사용자가 친구 또는 일반 사용자를 신고한 내역을 저장합니다.
 * - 신고 사유, 신고자-피신고자 관계, 처리 상태 등의 정보를 포함합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "FRIEND_REPORT",
        uniqueConstraints = @UniqueConstraint(name = "UK_REPORTER_REPORTED", columnNames = {"REPORTER_ID", "REPORTED_ID"})
)
public class FriendReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_report_seq_generator")
    @SequenceGenerator(name = "friend_report_seq_generator", sequenceName = "FRIEND_REPORT_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 신고한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID", nullable = false)
    private User reporter;

    /**
     * 신고당한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTED_ID", nullable = false)
    private User reported;

    /**
     * 신고 사유
     */
    @Column(name = "REASON", nullable = false, length = 500)
    private String reason;

    /**
     * 신고 시각
     */
    @Column(name = "REPORTED_AT", nullable = false)
    private LocalDateTime reportedAt;

    /**
     * 신고 처리 상태 (예: PENDING, RESOLVED)
     */
    @Column(name = "STATUS", nullable = false, length = 50)
    private String status;

    /**
     * 생성 시 자동으로 신고 시각 및 초기 상태 설정
     */
    @PrePersist
    protected void onCreate() {
        this.reportedAt = LocalDateTime.now();
        this.status = "PENDING"; // 기본 상태값
    }
}