package com.smartcampus.back.chat.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 채팅 메시지 엔티티
 *
 * 텍스트 메시지와 파일 첨부 메시지를 모두 처리합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 메시지가 속한 채팅방
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    /**
     * 메시지를 보낸 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    /**
     * 메시지 텍스트 (파일 메시지일 경우 null 가능)
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 메시지 전송 시각
     */
    @CreationTimestamp
    private LocalDateTime sentAt;

    /**
     * 첨부파일 포함 여부
     */
    @Column(nullable = false)
    private boolean hasFile;

    /**
     * 첨부된 파일 엔티티
     */
    @OneToOne(mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatFile chatFile;

    /**
     * 첨부파일 원본 이름 반환 (없으면 null)
     */
    public String getFileName() {
        return chatFile != null ? chatFile.getOriginalName() : null;
    }

    /**
     * 첨부파일 MIME 타입 반환 (없으면 null)
     */
    public String getContentType() {
        return chatFile != null ? chatFile.getContentType() : null;
    }

    /**
     * 첨부파일 미리보기 URL 반환 (없으면 null)
     */
    public String getPreviewUrl() {
        return chatFile != null ? "/api/chat/messages/files/" + this.id : null;
    }

    /**
     * 첨부파일 다운로드 URL 반환 (없으면 null)
     */
    public String getDownloadUrl() {
        return chatFile != null ? "/api/chat/messages/" + this.id + "/download" : null;
    }
}
