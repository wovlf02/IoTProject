package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_msg_seq_gen")
    @SequenceGenerator(name = "chat_msg_seq_gen", sequenceName = "chat_msg_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "message", nullable = false, length = 4000)
    private String message;

    @Column(name = "type", nullable = false, length = 20)
    private String type;  // TEXT / IMAGE / FILE

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
}
