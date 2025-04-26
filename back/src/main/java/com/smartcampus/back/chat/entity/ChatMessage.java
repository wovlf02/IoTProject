package com.smartcampus.back.chat.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ChatMessage
 * <p>
 * 채팅방 내 메시지(텍스트, 파일 등)를 저장하는 엔티티입니다.
 * 텍스트, 파일 메시지 구분과 soft delete 지원을 포함합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_messages")
public class ChatMessage {

    /**
     * 메시지 고유 ID
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
     * 메시지를 보낸 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    /**
     * 메시지 텍스트 (텍스트 메시지인 경우에만 사용)
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 연결된 첨부파일 ID (첨부파일 메시지인 경우)
     */
    private Long attachmentId;

    /**
     * 메시지 타입 (TEXT, FILE 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;

    /**
     * 메시지 발송 시간
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;

    /**
     * 메시지 삭제 여부 (soft delete)
     */
    @Column(nullable = false)
    private boolean deleted;

    /**
     * 최초 저장 시 sentAt 설정
     */
    @PrePersist
    protected void onCreate() {
        this.sentAt = LocalDateTime.now();
        this.deleted = false;
    }

    /**
     * 메시지 타입 ENUM
     */
    public enum MessageType {
        TEXT, FILE
    }
}
