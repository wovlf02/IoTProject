package com.smartcampus.back.post.entity;

import com.smartcampus.back.post.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 신고 엔티티
 * 게시글, 댓글, 대댓글에 대한 사용자 신고 내용을 저장
 */
@Entity
@Table(name = "post_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 신고자 사용자 ID
     */
    @Column(nullable = false)
    private Long reporterId;

    /**
     * 신고 대상 타입: POST, COMMENT, REPLY
     */
    @Column(nullable = false, length = 20)
    private String targetType;

    /**
     * 신고 대상 ID (게시글, 댓글, 대댓글 중 하나)
     */
    @Column(nullable = false)
    private Long targetId;

    /**
     * 신고 유형 (예: SPAM, ABUSE 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReportType type;

    /**
     * 신고 사유 (선택 입력)
     */
    @Column(length = 1000)
    private String reason;

    /**
     * 신고 접수 시간
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 연결된 게시글 (POST 타입일 경우에만 해당)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
