package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * FriendRequest 엔티티 클래스
 *
 * 사용자가 친구 요청을 보낸 정보를 저장하는 테이블 (friend_requests)와 매핑
 * 친구 요청을 보낸 사용자(sender)와 받은 사용자(receiver) 간의 관계 설정
 * 요청 상태 (보류, 수락, 거절) 포함
 * 요청 생성 일시 포함
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
     * 친구 요청 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 친구 요청을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    /**
     * 친구 요청을 보낸 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 친구 요청을 보낼 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REQUEST_SENDER"))
    private User sender;

    /**
     * 친구 요청을 받은 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자는 여러 친구 요청을 받을 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REQUEST_RECEIVER"))
    private User receiver;

    /**
     * 친구 요청 상태
     *
     * pending (대기 중) / accepted (수락됨) / rejected (거절됨) 중 하나
     * CHECK 제약 조건을 추가하여 유효성 검사
     */
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    /**
     * 친구 요청 생성 시간 (자동 설정)
     *
     * 사용자가 친구 요청을 보낸 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    /**
     * 친구 요청 상태를 관리하는 Enum
     *
     * PENDING: 요청 대기 중
     * ACCEPTED: 요청 수락됨
     * REJECTED: 요청 거절됨
     */
    public enum RequestStatus {
        PENDING, ACCEPTED, REJECTED
    }
}
