package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

/**
 * ChatRoom 엔티티 클래스
 *
 * 사용자가 참여하는 채팅방 정보를 저장하는 테이블 (chat_rooms)과 매핑
 * 1:1 채팅과 그룹 채팅을 지원
 * 채팅방 이름 및 생성 시각 포함
 * 1:1 채팅일 경우 상대방 닉네임을 자동으로 채팅방 이름(roomName)으로 설정
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
     * 채팅방 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 채팅방을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * 채팅방 이름
     *
     * 그룹 채팅방의 경우 사용자가 지정한 이름
     * 1:1 채팅의 경우 상대방의 닉네임으로 자동 설정됨
     */
    @Column(name = "room_name", length = 100)
    private String roomName;

    /**
     * 채팅방 유형 (1:1 채팅 또는 그룹 채팅 여부)
     *
     * Default: false (1:1 채팅)
     * 그룹 채팅방일 경우 true로 설정됨
     */
    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    /**
     * 채팅방 생성 시간 (자동 설정)
     *
     * 채팅방이 처음 생성된 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    /**
     * 채팅방에 참여한 사용자 목록
     *
     * ChatMember 엔티티와 1:N 관계
     * 해당 채팅방에 포함된 사용자 정보 저장
     */
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMember> members;

    /**
     * 채팅방 내 메시지 목록
     *
     * ChatMessage 엔티티와 1:N 관계
     * 해당 채팅방에서 주고받은 메시지 기록 저장
     */
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages;

    /**
     * 1:1 채팅방의 경우, 상대방의 닉네임을 자동으로 roomName에 설정
     *
     * 채팅방이 저장되기 전에 실행되는 JPA 콜백 메서드 (@PrePersist)
     * 1:1 채팅이고 roomName이 설정되지 않았을 경우, 자동으로 상대방 닉네임을 설정
     */
    @PrePersist
    public void setRoomNameForPrivateChat() {
        if(!isGroup && (roomName == null || roomName.isBlank()) && members != null && members.size() == 2) {
            // 상대방의 닉네임을 가져와서 채팅방 이름으로 설정
            User otherUser = members.stream()
                    .map(ChatMember::getUser)
                    .filter(user -> !user.getUsername().equals("현재 로그인한 사용자")) // 현재 사용자를 제외
                    .findFirst()
                    .orElse(null);
            if(otherUser != null) {
                this.roomName = otherUser.getNickname();
            }
        }
    }
}
