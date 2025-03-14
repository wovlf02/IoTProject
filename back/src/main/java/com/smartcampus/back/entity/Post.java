package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Post 엔티티 클래스
 * posts 테이블과 매핑됨
 * 사용자(User)와 N:1 관계
 * 댓글(Comment), 추천(Like), 첨부파일(Attachment)와 연관됨
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    /**
     * 게시글 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     * 각 게시글은 고유한 ID를 가짐
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    /**
     * 게시글 작성자 (User ID)
     * users 테이블과 FK 관계 (Many-to-One)
     * 작성자가 탈퇴해도 게시글은 유지됨 (SET NULL)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 게시글 제목
     * 최대 200자 제한
     * 필수 입력 (Not Null)
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * 게시글 내용
     * 긴 텍스트 저장을 위해 CLOB 사용
     * 필수 입력 (Not Null)
     */
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 게시글 생성 시각
     * Default: 현재 시각
     * 게시글 작성 시 자동 설정
     * 수정 불가 (updateable = false)
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 게시글 수정 시각
     * 게시글이 수정될 때 자동 갱신됨
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
