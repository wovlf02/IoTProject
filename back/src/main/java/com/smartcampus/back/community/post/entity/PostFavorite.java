package com.smartcampus.back.community.post.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시글 즐겨찾기 엔티티
 * <p>
 * 사용자가 게시글을 즐겨찾기에 추가한 내역을 저장합니다.
 * 하나의 사용자와 하나의 게시글 간에 하나의 즐겨찾기만 존재할 수 있습니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "post_favorite",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})
)
public class PostFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 즐겨찾기를 등록한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 즐겨찾기된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * 즐겨찾기 등록 시간
     */
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
