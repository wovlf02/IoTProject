package com.smartcampus.back.notification.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Notification 엔티티
 * <p>
 * 사용자가 수신한 푸시 알림 정보를 저장합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    /**
     * 알림 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 알림을 수신한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User receiver;

    /**
     * 알림 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 알림 메시지 본문
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    /**
     * 알림 생성 시각
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 읽음 여부
     */
    @Column(nullable = false)
    private boolean isRead;

    /**
     * 알림 유형 (예: FRIEND_REQUEST, MESSAGE, SYSTEM 등)
     */
    @Column(nullable = false)
    private String type;

    /**
     * 참조되는 리소스 ID (예: 게시글 ID, 채팅방 ID 등)
     */
    private Long referenceId;

    /**
     * 참조되는 리소스 URL (클라이언트에서 해당 링크로 이동 가능)
     */
    private String referenceUrl;

    /**
     * 알림 읽음 처리
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * 알림 생성 편의 메서드
     */
    public static Notification create(User receiver, String title, String body, String type, Long referenceId, String referenceUrl) {
        return Notification.builder()
                .receiver(receiver)
                .title(title)
                .body(body)
                .type(type)
                .referenceId(referenceId)
                .referenceUrl(referenceUrl)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
    }
}
