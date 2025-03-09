package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ChatReadReceipt 엔티티 클래스
 * chat_read_receipts 테이블과 매핑됨
 * 사용자가 특정 메시지를 읽었는지 확인하는 데이터 저장
 */
@Entity
@Table(name = "chat_read_receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatReadReceipt {

    /**
     * 메시지 ID (ChatMessage)
     * chat_messages 테이블과 FK 관계 (Many-to-One)
     * 읽음 확인 대상 메시지
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private ChatMessage message;

    /**
     * 사용자 ID (User)
     * users 테이블과 FK 관계 (Many-to-One)
     * 해당 메시지를 읽은 사용자
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 읽음 확인 시간
     * Default: 현재 시각
     * 사용자가 메시지를 읽은 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "read_at", nullable = false, updatable = false)
    private LocalDateTime readAt;
}
