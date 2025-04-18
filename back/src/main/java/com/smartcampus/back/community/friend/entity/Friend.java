package com.smartcampus.back.community.friend.entity;

import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 친구 관계 엔티티
 * <p>
 * 사용자 간 상호 친구 관계를 나타냅니다.
 * A가 B를 친구로 등록하면, B도 A의 친구가 됩니다 (양방향).
 * </p>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "friends", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_a_id", "user_b_id"})
})
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 친구 관계의 한쪽 사용자 (A)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id", nullable = false)
    private User userA;

    /**
     * 친구 관계의 다른쪽 사용자 (B)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id", nullable = false)
    private User userB;

    /**
     * 친구가 된 시간
     */
    private LocalDateTime createdAt;

    /**
     * 친구 관계 생성 시 시간 자동 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
