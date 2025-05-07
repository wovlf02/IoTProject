package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_report_seq_gen")
    @SequenceGenerator(name = "community_report_seq_gen", sequenceName = "community_report_seq", allocationSize = 1)
    private Long id;

    @Column(name = "target_type", nullable = false, length = 30)
    private String targetType;  // 예: POST, COMMENT, USER

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "status", nullable = false, length = 20)
    private String status;  // PENDING, APPROVED, REJECTED
}
