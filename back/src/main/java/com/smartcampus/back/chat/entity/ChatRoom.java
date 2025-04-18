package com.smartcampus.back.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 채팅방 엔티티
 *
 * 게시글 기반 채팅, 그룹 채팅, 1:1 채팅 등 다양한 유형의 방을 관리합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 채팅방 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 채팅방 유형 (POST, STUDY, GROUP, DIRECT 등)
     */
    @Column(nullable = false)
    private String roomType;

    /**
     * 연동 객체 ID (예: 게시글 ID, 그룹 ID, 1:1 채팅 시 null 가능)
     */
    private Long refId;

    /**
     * 생성 시각
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 마지막 메시지 전송 시각
     */
    private LocalDateTime lastMessageAt;

    /**
     * 메시지 목록 (양방향 설정 시 사용)
     */
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    /**
     * 참가자 목록 (양방향 설정 시 사용)
     */
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipant> participants = new ArrayList<>();
}
