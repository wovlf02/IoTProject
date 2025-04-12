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
@ToString(exclude = "post") // 순환 참조 방지 (선택)
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
     * 서버에 저장된 UUID 기반의 파일 이름
     */
    @Column(nullable = false)
    private String storedName;

    /**
     * 저장된 전체 파일 경로 (로컬 절대경로 또는 S3 경로 등)
     */
    @Column(nullable = false)
    private String filePath;

    /**
     * 파일 크기 (바이트)
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * 파일 타입 (MIME 또는 확장자)
     */
    @Column(nullable = false)
    private String fileType;

    /**
     * 이 파일이 속한 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
