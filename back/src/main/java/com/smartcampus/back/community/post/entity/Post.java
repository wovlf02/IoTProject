package com.smartcampus.back.community.post.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.post.enums.PostCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시글(Post) 엔티티
 * <p>
 * 커뮤니티에 등록된 게시글 정보를 저장합니다.
 * 하나의 게시글은 하나의 작성자(User)와 연관되며,
 * 제목, 내용, 카테고리, 조회수, 좋아요 수 등의 정보를 포함합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {

    /**
     * 게시글 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글 제목
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * 게시글 본문 내용
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * 게시글 카테고리 (예: 자유, 정보공유, 질문)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PostCategory category;

    /**
     * 게시글 작성자 (User)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    /**
     * 게시글 등록일시
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 게시글 최종 수정일시
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 게시글 조회수
     */
    @Column(nullable = false)
    private int viewCount;

    /**
     * 좋아요 수
     */
    @Column(nullable = false)
    private int likeCount;

    /**
     * 소프트 삭제 여부
     */
    @Column(nullable = false)
    private boolean deleted;

    /**
     * 게시글 수정 처리
     */
    public void update(String title, String content, PostCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 게시글 삭제 처리 (soft delete)
     */
    public void delete() {
        this.deleted = true;
    }

    /**
     * 조회수 증가
     */
    public void increaseViewCount() {
        this.viewCount++;
    }

    /**
     * 좋아요 수 증가
     */
    public void increaseLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 수 감소
     */
    public void decreaseLikeCount() {
        if (this.likeCount > 0) this.likeCount--;
    }
}
