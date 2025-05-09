package com.smartcampus.back.entity.friend;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자 차단(FriendBlock) 엔티티
 * - 친구 여부와 관계없이 사용자가 특정 사용자를 차단한 내역을 저장합니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "FRIEND_BLOCK",
        uniqueConstraints = @UniqueConstraint(name = "UK_BLOCKER_BLOCKED", columnNames = {"BLOCKER_ID", "BLOCKED_ID"})
)
public class FriendBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_block_seq_generator")
    @SequenceGenerator(name = "friend_block_seq_generator", sequenceName = "FRIEND_BLOCK_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 차단한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOCKER_ID", nullable = false)
    private User blocker;

    /**
     * 차단된 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOCKED_ID", nullable = false)
    private User blocked;

    /**
     * 차단 일시
     */
    @Column(name = "BLOCKED_AT", nullable = false)
    private LocalDateTime blockedAt;

    /**
     * 차단 생성 날짜 설정
     */
    @PrePersist
    protected void onCreate() {
        this.blockedAt = LocalDateTime.now();
    }
}