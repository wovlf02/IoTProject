package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * ChatReadReceipt 엔티티 클래스
 *
 * 사용자가 특정 채팅 메시지를 읽었는지 기록하는 테이블 (chat_read_receipts)과 매핑
 * 특정 메시지(ChatMessage)와 사용자(User) 간의 관계 설정
 * 중복 방지를 위해 message_id와 user_id를 복합키로 설정
 * 메시지 읽음 시간 포함
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
     * 읽은 메시지 ID (Foreign Key)
     *
     * ChatMessage 테이블의 messgae_id(chat_messages.message_id)와 연결
     * ManyToOne 관계 설정 (하나의 메시지를 여러 사용자가 읽을 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false, foreignKey = @ForeignKey(name = "FK_READ_RECEIPT_MESSAGE"))
    private ChatMessage chatMessage;

    /**
     * 읽은 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 개의 메시지를 읽을 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_READ_RECEIPT_USER"))
    private User user;

    /**
     * 메시지 읽음 시간 (자동 설정)
     *
     * 사용자가 해당 메시지를 읽은 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "read_at", updatable = false)
    private Timestamp readAt;

    /**
     * 복합 키 (message_id, user_id) 설정
     *
     * 동일한 사용자가 동일한 메시지를 읽은 경우 중복 방지
     */
    @Embeddable
    @Data
    public static class ChatReadReceiptId implements java.io.Serializable {
        private Long chatMessage;
        private Long user;
    }

    @EmbeddedId
    private ChatReadReceiptId id;
}
