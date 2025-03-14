package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Friend엔티티 클래스
 * friends 테이블과 매핑됨
 * 사용자(User) 간의 친구 관계를 저장하는 엔티티
 * (user_id, friend_id) 복합 Primary Key를 사용하여 중복 방지
 */
@Entity
@Table(name = "friends", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "friend_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {

    /**
     * 친구 관계 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long id;

    /**
     * 사용자 ID U(User)
     * users 테이블과 FK 관계 (Many-to-One)
     * 친구 요청을 보낸 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 친구 ID (User)
     * users 테이블과 FK 관계 (Many-to-One)
     * 친구 요청을 받은 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    /**
     * 친구 관계 생성 시각
     * Default: 현재 시각
     * 사용자가 친구 요청을 수락한 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
