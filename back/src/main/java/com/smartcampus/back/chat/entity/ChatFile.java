package com.smartcampus.back.chat.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 채팅 메시지에 첨부된 파일 메타데이터를 저장하는 엔티티
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 원본 파일명
     */
    @Column(nullable = false)
    private String originalName;

    /**
     * 서버 저장 경로 또는 S3 키
     */
    @Column(nullable = false)
    private String storedPath;

    /**
     * MIME 타입 (image/png, application/pdf 등)
     */
    @Column(nullable = false)
    private String contentType;

    /**
     * 파일 크기 (bytes)
     */
    @Column(nullable = false)
    private Long size;

    /**
     * 해당 파일이 포함된 채팅 메시지 (1:1 관계)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;
}
