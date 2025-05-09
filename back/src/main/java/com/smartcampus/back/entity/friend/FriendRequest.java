package com.smartcampus.back.entity.friend;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 친구 요청(FriendRequest) 엔티티
 * - 사용자가 특정 사용자에게 친구 요청을 보낸 내역을 저장합니다.
 * - 요청 상태 및 생성 시각 등 정보를 포함합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "FRIEND_REQUEST",
        uniqueConstraints = @UniqueConstraint(name = "UK_SENDER_RECEIVER", columnNames = {"SENDER_ID", "RECEIVER_ID"})
)
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_request_seq_generator")
    @SequenceGenerator(name = "friend_request_seq_generator", sequenceName = "FRIEND_REQUEST_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 친구 요청 전송 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", nullable = false)
    private User sender;

    /**
     * 친구 요청 수신 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", nullable = false)
    private User receiver;

    /**
     * 요청 생성 시각
     */
    @Column(name = "REQUESTED_AT", nullable = false)
    private LocalDateTime requestedAt;

    /**
     * 요청 상태 (예: PENDING, ACCEPTED, REJECTED)
     */
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    /**
     * 요청 생성 시 기본 정보 초기화
     */
    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now(); // 요청 생성 시각 자동 설정
        this.status = "PENDING"; // 기본 상태 "PENDING"
    }

    /**
     * 요청 생성 시각 반환
     * - 추가적인 Getter 메소드. 필요 시 DTO 변환에 사용 가능.
     */
    public LocalDateTime getRequestedAt() {
        return this.requestedAt;
    }
}