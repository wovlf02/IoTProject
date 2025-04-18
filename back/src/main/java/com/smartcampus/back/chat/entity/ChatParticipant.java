package com.smartcampus.back.chat.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 참가자 엔티티
 *
 * 채팅방별 사용자 입장 정보 및 읽음 상태 등을 저장합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chat_room_id", "user_id"})
})
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 참가한 채팅방
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    /**
     * 참가자 (User)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 마지막으로 읽은 메시지 ID (읽음 처리용)
     */
    private Long lastReadMessageId;

    /**
     * 입장 시각
     */
    private LocalDateTime joinedAt;

    /**
     * 퇴장 시각 (null이면 현재 참여 중)
     */
    private LocalDateTime exitedAt;

    /**
     * 현재 참여 상태 여부
     */
    @Column(nullable = false)
    private boolean active;
}
