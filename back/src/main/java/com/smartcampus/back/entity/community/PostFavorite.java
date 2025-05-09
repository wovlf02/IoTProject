package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시글 즐겨찾기(PostFavorite) 엔티티
 * - 특정 사용자가 게시글을 즐겨찾기에 추가한 기록을 나타냅니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "POST_FAVORITE",
    uniqueConstraints = @UniqueConstraint(name = "UK_USER_POST", columnNames = {"USER_ID", "POST_ID"})
)
public class PostFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_favorite_seq_generator")
    @SequenceGenerator(name = "post_favorite_seq_generator", sequenceName = "POST_FAVORITE_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 즐겨찾기한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * 즐겨찾기된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    /**
     * 즐겨찾기 추가 날짜
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}