package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * ChatMember 엔티티 클래스
 *
 * 특정 채팅방(ChatRoom)에 속한 사용자의 정보를 저장하는 테이블 (chat_members)과 매핑
 * 사용자가 여러 개의 채팅방에 참여 가능 (ManyToOne 관계)
 * 채팅방 내 역할 (일반 멤버, 관리자) 저장
 * 채팅방 참여 시간 포함
 */
@Entity
@Table(name = "chat_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMember {

    /**
     * 채팅방 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 채팅방 참여 정보를 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_member_id")
    private Long chatMemberId;

    /**
     * 채팅방 ID (Foreign Key)
     *
     * ChatRoom 테이블의 chat_id(chat_rooms.chat_id)와 연결
     * ManyToOne 관계 설정 (한 채팅방에 여러 사용자가 참여 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CHAT_MEMBER_ROOM"))
    private ChatRoom chatRoom;

    /**
     * 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 개의 채팅방에 참여 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,foreignKey = @ForeignKey(name = "FK_CHAT_MEMBER_USER"))
    private User user;

    /**
     * 채팅방 내 역할
     *
     * member (일반 사용자) 또는 admin (관리자) 역할 지정 가능
     * Default: member
     */
    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ChatRole role = ChatRole.MEMBER;

    /**
     * 채팅방 참여 시간 (자동 설정)
     *
     * 사용자가 채팅방에 처음 참여한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private Timestamp joinedAt;

    /**
     * 채팅방 내 사용자 역할을 관리하는 Enum
     *
     * MEMBER: 일반 사용자
     * ADMIN: 채팅방 관리자
     */
    public enum ChatRole {
        MEMBER, ADMIN
    }
}
