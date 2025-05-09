package com.smartcampus.back.entity.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_seq_generator")
    @SequenceGenerator(name = "chat_room_seq_generator", sequenceName = "CHAT_ROOM_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 채팅방 이름
     */
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    /**
     * 채팅방 유형 (POST, GROUP, STUDY, DIRECT 등)
     */
    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;

    /**
     * 연동된 리소스 ID (예: 게시글 ID, 그룹 ID 등)
     */
    @Column(name = "REFERENCE_ID")
    private Long referenceId;

    /**
     * 채팅방 생성 시각
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipant> participants = new ArrayList<>();
}