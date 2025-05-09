package com.smartcampus.back.entity.friend;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 친구 관계(Friend) 엔티티
 * - 사용자가 등록한 친구 관계 정보를 나타냅니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "FRIEND",
        uniqueConstraints = @UniqueConstraint(name = "UK_USER_FRIEND", columnNames = {"USER_ID", "FRIEND_ID"})
)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_seq_generator")
    @SequenceGenerator(name = "friend_seq_generator", sequenceName = "FRIEND_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 사용자 (친구 요청을 보낸 사용자)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * 친구 (친구 요청을 받은 사용자)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FRIEND_ID", nullable = false)
    private User friend;

    /**
     * 친구 관계 생성 날짜
     */
    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    /**
     * 친구 관계 생성 시 날짜 초기화
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now().toString();
    }
}