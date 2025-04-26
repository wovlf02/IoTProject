package com.smartcampus.back.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ChatRoom
 * <p>
 * 채팅방 정보를 저장하는 엔티티입니다.
 * (1:1 채팅방, 소규모 채팅방에 사용됩니다.)
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_rooms")
public class ChatRoom {

    /**
     * 채팅방 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 채팅방 이름 (1:1 대화일 경우 친구 이름 기반으로 설정 가능)
     */
    @Column(nullable = false)
    private String name;

    /**
     * 채팅방 타입 (1:1, GROUP 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    /**
     * 채팅방 생성 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 최초 저장 시 createdAt 자동 세팅
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 채팅방 타입 ENUM
     */
    public enum RoomType {
        DIRECT,    // 1:1 채팅
        GROUP      // 그룹 채팅 (optional future 확장)
    }
}
