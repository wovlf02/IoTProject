package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 댓글 엔티티
 * 게시글에 작성된 댓글 정보 저장
 */
@Entity
@Table(name = "post_comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 댓글 내용
     */
    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * 댓글 작성자 ID
     */
    @Column(nullable = false)
    private Long writerId;

    /**
     * 작성된 시간
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 연결된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 대댓글 리스트
     */
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;
}
