package com.smartcampus.back.chat.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 채팅 메시지 엔티티
 *
 * 텍스트 또는 파일 메시지를 저장합니다.
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
     * 전송 시각
     */
    @CreationTimestamp
    private LocalDateTime sentAt;

    /**
     * 파일 메시지 여부
     */
    @Column(nullable = false)
    private boolean hasFile;

    /**
     * 첨부된 파일 정보 (있을 경우)
     */
    @OneToOne(mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatFile chatFile;
}
