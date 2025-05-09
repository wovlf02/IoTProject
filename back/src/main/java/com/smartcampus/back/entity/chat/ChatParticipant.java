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
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_participant_seq_generator")
    @SequenceGenerator(name = "chat_participant_seq_generator", sequenceName = "CHAT_PARTICIPANT_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 참여한 채팅방
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    /**
     * 참여자 (User)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    /**
     * 입장 시각
     */
    @Column(name = "JOINED_AT", nullable = false)
    private LocalDateTime joinedAt;
}