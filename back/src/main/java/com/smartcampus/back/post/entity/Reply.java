package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 대댓글(답글) 엔티티
 * 댓글에 달리는 답글 정보를 저장합니다.
 */
@Entity
@Table(name = "comment_replies") // ✅ 'schema' → 'name' 으로 수정
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "comment") // 순환참조 방지 (optional)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 대댓글 내용
     */
    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * 작성자 ID
     */
    @Column(nullable = false)
    private Long writerId;

    /**
     * 생성 시각
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정 시각
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * 연결된 댓글 (대댓글은 특정 댓글에만 달림)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}
