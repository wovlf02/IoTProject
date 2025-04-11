package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 게시글 좋아요 엔티티
 * 사용자가 게시글에 누른 좋아요 정보 저장
 */
@Entity
@Table(name = "post_likes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LikeId.class)
public class Like {

    /**
     * 사용자 ID (복합키)
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 게시글 ID (복합키)
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 좋아요 누른 시각
     */
    @CreationTimestamp
    private LocalDateTime createdAt;
}
