package com.smartcampus.back.notification.entity;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Notification 엔티티
 * <p>
 * 사용자에게 발송된 알림 정보를 저장하는 테이블입니다.
 * 알림은 다양한 타입(COMMENT, REPLY, FRIEND, CHAT, NEXT_CLASS, LATE_WARNING 등)을 가질 수 있습니다.
 * 읽음 여부, 생성 시각 등을 함께 관리합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {

    /**
     * 알림 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 알림을 받을 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 알림 제목
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * 알림 본문 내용
     */
    @Column(nullable = false, length = 500)
    private String body;

    /**
     * 알림 타입 (댓글, 대댓글, 친구, 채팅, 수업 알림 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    /**
     * 읽음 여부 (false = 읽지 않음, true = 읽음)
     */
    @Column(nullable = false)
    private Boolean isRead = false;

    /**
     * 알림 생성 시각
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
