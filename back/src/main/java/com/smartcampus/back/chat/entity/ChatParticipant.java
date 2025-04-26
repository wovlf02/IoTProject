package com.smartcampus.back.chat.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ChatParticipant
 * <p>
 * 채팅방에 참여하고 있는 사용자를 관리하는 엔티티입니다.
 * 나가기(soft delete), 알림 설정 여부 등을 관리합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_participants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
public class ChatParticipant {

    /**
     * 참가자 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 연결된 채팅방 ID
     */
    @Column(name = "room_id", nullable = false)
    private Long roomId;

    /**
     * 참가자 (User)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 채팅방 알림 설정 (ON/OFF)
     */
    @Column(nullable = false)
    private boolean notificationsEnabled;

    /**
     * 참가 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    /**
     * 퇴장 일시 (null이면 아직 참여 중)
     */
    private LocalDateTime exitedAt;

    /**
     * 최초 저장 시 joinedAt 자동 세팅
     */
    @PrePersist
    protected void onCreate() {
        this.joinedAt = LocalDateTime.now();
        this.notificationsEnabled = true; // 기본값: 알림 ON
    }

    /**
     * 소프트 퇴장 처리
     */
    public void exitRoom() {
        this.exitedAt = LocalDateTime.now();
    }

    /**
     * 알림 설정 변경
     */
    public void toggleNotifications(boolean enabled) {
        this.notificationsEnabled = enabled;
    }
}
