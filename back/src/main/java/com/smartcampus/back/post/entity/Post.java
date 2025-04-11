package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 엔티티
 * 사용자가 작성한 게시글 정보를 저장
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 게시글 본문 내용
     */
    @Column(nullable = false, columnDefinition = "CLOB")
    private String content;

    /**
     * 작성자 ID
     */
    @Column(nullable = false)
    private Long writerId;

    /**
     * 게시글 공개 여부
     */
    @Column(nullable = false)
    private boolean isPublic;

    /**
     * 조회수
     */
    @Column(nullable = false)
    private int viewCount = 0;

    /**
     * 생성 시간
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 수정 시간
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * 첨부파일 목록 (1:N)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    /**
     * 댓글 목록 (1:N)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    /**
     * 좋아요 목록 (1:N)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    /**
     * 게시글 신고 목록 (1:N)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;
}
