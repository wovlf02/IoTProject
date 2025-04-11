package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 대댓글(답글) 엔티티
 * 댓글에 달리는 답글(Reply)을 저장
 */
@Entity
@Table(schema = "comment_replies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createdAt;

    /**
     * 연결된 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
