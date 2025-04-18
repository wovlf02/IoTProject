package com.smartcampus.back.community.attachment.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 첨부파일 엔티티
 * 게시글, 댓글, 대댓글에 첨부된 파일 정보를 저장합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 실제 저장된 파일 경로
     */
    @Column(nullable = false)
    private String filePath;

    /**
     * 저장된 파일명 (UUID 등)
     */
    @Column(nullable = false)
    private String savedName;

    /**
     * 원본 파일명
     */
    @Column(nullable = false)
    private String originalName;

    /**
     * MIME 타입 (image/png, application/pdf 등)
     */
    private String contentType;

    /**
     * 파일 크기 (바이트)
     */
    private long size;

    /**
     * 업로드한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    /**
     * 게시글 연관 (nullable: 댓글/대댓글일 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 댓글 연관 (nullable: 게시글/대댓글일 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * 대댓글 연관 (nullable: 게시글/댓글일 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Comment reply;

    /**
     * 업로드 시간
     */
    private LocalDateTime uploadedAt;
}
