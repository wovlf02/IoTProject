package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ChatRoom 엔티티 클래스
 * chat_rooms 테이블과 매핑됨
 * 1:1 및 그룹 채팅방을 저장하는 엔티티
 * 참여자는 ChatMember 엔티티에서 관리됨
 */
@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    /**
     * 채팅방 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    /**
     * 채팅방 이름
     * 그룹 채팅일 경우 설정 가능
     * 1:1 채팅일 경우 NULL 허용
     */
    @Column(name = "room_name", length = 100)
    private String roomName;

    /**
     * 채팅방 유형 (1:1 또는 그룹)
     * 0: 1:1 채팅
     * 1: 그룹 채팅
     * Default: 0 (1:1 채팅)
     */
    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    /**
     * 채팅방 생성 시각
     * Default: 현재 시각
     * 채팅방이 생성된 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 채팅방에 속한 멤버들
     * ChatMember 엔티티와 연관됨
     * OneToMany 관계 (채팅방 하나에 여러 명의 멤버가 포함됨)
     */
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMember> members;
}
