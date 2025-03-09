package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * PostLike 엔티티 클래스
 * post_likes 테이블과 매핑됨
 * 사용자가 특정 게시글(Post)에 추천을 누른 기록을 저장
 * 사용자(User)와 게시글(Post)의 다대다(N:M) 관계를 해결하는 중간 테이블
 * 같은 사용자(user_id)가 같은 게시글(post_id)에 중복 추천을 할 수 없음 (복합 Primary Key 설정)
 */
@Entity
@Table(name = "post_likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {

    /**
     * 좋아요를 누른 사용자 ID
     * users 테이블과 FK 관계 (Many-to-One)
     * 한 명의 사용자가 여러 게시글을 추천할 수 있음
     * 연관된 사용자가 삭제되면 추천 기록도 삭제됨 (CascadeType.ALL)
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 추천이 눌린 게시글 ID
     * posts 테이블과 FK 관계 (Many-to-One)
     * 한 게시글은 여러 사용자가 추천을 할 수 있음
     * 게시글이 삭제되면 해당 게시글의 추천 기록도 삭제됨 (CascadeType.ALL)
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * 추천 등록 시각
     * Default: 현재 시각
     * 사용자가 추천을 누른 시점을 자동으로 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 복합 키 설정 (user_id + post_id)
     * 같은 사용자가 같은 게시글에 여러 번 추천을 누를 수 없도록 설정
     */
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostLikeId implements java.io.Serializable {
        private Long user;
        private Long post;
    }

    /**
     * 복합 키 적용
     */
    @EmbeddedId
    private PostLikeId id;
}
