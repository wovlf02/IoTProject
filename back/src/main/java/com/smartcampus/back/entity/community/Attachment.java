package com.smartcampus.back.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * 첨부파일(Attachment) 엔티티
 */
@Entity
@Table(name = "ATTACHMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_seq_generator")
    @SequenceGenerator(name = "attachment_seq_generator", sequenceName = "ATTACHMENT_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 사용자가 업로드한 원본 파일명
     */
    @Column(name = "ORIGINAL_FILE_NAME", nullable = false, length = 255)
    private String originalFileName;

    /**
     * 서버에 저장된 파일명 (UUID 포함)
     */
    @Column(name = "STORED_FILE_NAME", nullable = false, length = 500)
    private String storedFileName;

    /**
     * MIME 타입 (예: image/png, application/pdf 등)
     */
    @Column(name = "CONTENT_TYPE", length = 100)
    private String contentType;

    /**
     * 이미지 미리보기 가능 여부 (프론트에서 썸네일 지원 용도)
     */
    @Column(name = "PREVIEW_AVAILABLE", nullable = false)
    private boolean previewAvailable;

    /**
     * 첨부파일이 연결된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    /**
     * 첨부파일이 연결된 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    /**
     * 첨부파일이 연결된 대댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;

    /**
     * 파일 저장 경로
     */
    @Transient
    @Value("${file.storage.base-url:/uploads}")
    private String baseUrl;

    /**
     * 파일 URL 반환
     * - 파일이 저장된 서버의 URL 경로를 반환합니다.
     */
    public String getFileUrl() {
        return String.format("%s/%s", baseUrl, storedFileName);
    }
}