package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ChatMember 엔티티 클래스
 * chat_members 테이블과 매핑됨
 * 사용자(User)와 채팅방(ChatRoom) 간의 관계를 관리
 * 채팅방 내 역할 (member/admin) 설정 가능
 */
@Entity
@Table(name = "chat_members", uniqueConstraints = {@UniqueConstraint(columnNames = {"chat_id", "user_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMember {

    /**
     * 채팅방 참여 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_member_id")
    private Long id;

    /**
     * 채팅방 ID (ChatRoom)
     * chat_rooms 테이블과 FK 관계 (Many-to-One)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatRoom chatRoom;

    /**
     * 사용자 ID (User)
     * users 테이블과 FK 관계 (Many-to-One)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 채팅방 내 역할 (member/admin)
     * 기본값: member
     */
    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.MEMBER;

    /**
     * 채팅방 참여 시각
     * Default: 현재 시각
     * 사용자가 채팅방에 참여한 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    /**
     * 채팅방 내 역할 Enum
     * MEMBER: 일반 사용자
     * ADMIN: 관리자
     */
    public enum Role {
        MEMBER, ADMIN
    }
}
