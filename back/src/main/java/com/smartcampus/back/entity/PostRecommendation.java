package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * PostRecommendation 엔티티 클래스
 *
 * 사용자가 특정 게시글(Post)에 추천(좋아요)를 남긴 정보를 저장하는 테이블 (post_recommendations)과 매핑
 * User (추천을 누른 사용자)와 Post (추천된 게시글) 간의 관계 설정
 * 중복 추천 방지를 위해 user_id와 post_id를 복합키로 설정
 * 추천(좋아요) 생성 시각 포함
 */
@Entity
@Table(name = "post_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRecommendation {

    /**
     * 추천을 누른 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 게시글에 추천 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECOMMEND_USER"))
    private User user;

    /**
     * 추천받은 게시글 ID (Foreign Key)
     *
     * Post 테이블의 post_id(posts.post_id)와 연결
     * ManyToOne 관계 설정 (한 게시글에 여러 사용자가 추천 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECOMMEND_POST"))
    private Post post;

    /**
     * 추천(좋아요) 생성 시간 (자동 설정)
     *
     * 사용자가 추천을 누른 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    /**
     * 복합 키 (user_id, post_id) 설정
     *
     * 동일한 사용자가 동일한 게시글에 대해 중복 추천을 하지 못하도록 방지
     */
    @Embeddable
    @Data
    public static class PostRecommendationId implements java.io.Serializable {
        private Long user;
        private Long post;
    }

    @EmbeddedId
    private PostRecommendationId id;
}
