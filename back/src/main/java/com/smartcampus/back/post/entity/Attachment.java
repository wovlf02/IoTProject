package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 첨부파일 엔티티
 * 게시글에 업로드된 파일 정보 (이름, 경로, 사이즈, 타입 등)를 저장
 */
@Entity
@Table(name = "post_attachments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 원본 파일 이름
     */
    @Column(nullable = false)
    private String fileName;

    /**
     * 저장된 파일 경로
     */
    @Column(nullable = false)
    private String filePath;

    /**
     * 파일 사이즈 (byte)
     */
    private Long fileSize;

    /**
     * MIME 타입 또는 확장자
     */
    private String fileType;

    /**
     * 연결된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
