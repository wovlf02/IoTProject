package com.smartcampus.back.entity.chat;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq_generator")
    @SequenceGenerator(name = "chat_message_seq_generator", sequenceName = "CHAT_MESSAGE_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 채팅방 ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    /**
     * 메시지를 보낸 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    /**
     * 메시지 본문
     */
    @Column(nullable = false)
    private String content;

    /**
     * 메시지 타입 (TEXT, IMAGE, FILE)
     */
    @Column(nullable = false, length = 50)
    private String type;

    /**
     * 전송 시각
     */
    @Column(nullable = false)
    private LocalDateTime sentAt;

    /**
     * 저장된 첨부파일 이름 (type이 FILE인 경우 사용)
     */
    @Column(name = "STORED_FILE_NAME", length = 500)
    private String storedFileName;
}