package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * FriendRequest 엔티티 클래스
 * friend_requests 테이블과 매핑
 * 사용자(User)간 친구 요청을 저장
 */
@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {

    /**
     * 친구 요청 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    /**
     * 친구 요청을 모낸 사용자 (Sender)
     * users 테이블과 FK 관계 (Many-to-One)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    /**
     * 친구 요청을 받은 사용자 (Receiver)
     * users 테이블과 FK 관계 (Many-to-One)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    /**
     * 친구 요청 상태 (Status)
     * 요청 상태는 'pending', 'accepted', 'rejected' 중 하나
     * Default: 'pending'
     */
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    /**
     * 친구 요청이 생성된 시간
     * Default: 현재 시간
     * 친구 요청이 발생한 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 친구 요청 상태 Enum
     * PENDING: 대기 중
     * ACCEPTED: 수락됨
     * REJECTED: 거절됨
     */
    public enum RequestStatus {
        PENDING, ACCEPTED, REJECTED
    }
}
