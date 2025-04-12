package com.smartcampus.back.post.entity;

import com.smartcampus.back.post.enums.AttachmentTargetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 첨부파일 엔티티
 * 게시글/댓글/대댓글 등 다양한 객체에 첨부된 파일 정보를 저장
 */
@Entity
@Table(name = "attachments")
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
     * 첨부 대상 ID (게시글, 댓글, 대댓글 등 공통 사용)
     */
    @Column(nullable = false)
    private Long targetId;

    /**
     * 첨부 대상 타입 (게시글, 댓글, 대댓글 구분용)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttachmentTargetType targetType;

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
     * 저장된 전체 파일 경로 (로컬 경로 또는 S3 URL)
     */
    @Column(nullable = false)
    private String filePath;

    /**
     * 파일 크기 (바이트 단위)
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * 파일 MIME 타입 또는 확장자
     */
    @Column(nullable = false)
    private String fileType;

    /**
     * 업로드 시각
     */
    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
