package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_report_seq_gen")
    @SequenceGenerator(name = "admin_report_seq_gen", sequenceName = "admin_report_seq", allocationSize = 1)
    private Long id;

    @Column(name = "target_type", nullable = false, length = 30)
    private String targetType;  // POST, COMMENT, USER 등

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "status", nullable = false, length = 20)
    private String status;  // PENDING, APPROVED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private User processedBy;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
