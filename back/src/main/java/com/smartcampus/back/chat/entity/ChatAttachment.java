package com.smartcampus.back.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ChatAttachment
 * <p>
 * 채팅 메시지에 첨부된 파일(이미지, PDF 등)을 저장하는 엔티티입니다.
 * 파일 메타데이터와 업로드된 S3 URL 등을 관리합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_attachments")
public class ChatAttachment {

    /**
     * 첨부파일 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 연결된 채팅방 ID
     */
    @Column(nullable = false)
    private Long roomId;

    /**
     * 파일 원본 이름 (ex: original-file-name.pdf)
     */
    @Column(nullable = false)
    private String originalFileName;

    /**
     * 저장된 파일 경로 또는 S3 URL
     */
    @Column(nullable = false)
    private String fileUrl;

    /**
     * 파일 타입 (예: image/png, application/pdf)
     */
    @Column(nullable = false)
    private String contentType;

    /**
     * 파일 크기 (byte 단위)
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * 업로드 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    /**
     * 업로드 일시 자동 세팅
     */
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
}
