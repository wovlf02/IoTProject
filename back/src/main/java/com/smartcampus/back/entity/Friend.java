package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "friends",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_seq_gen")
    @SequenceGenerator(name = "friend_seq_gen", sequenceName = "friend_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 친구 요청을 보낸 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;  // 친구 대상 사용자

    @Column(name = "status", nullable = false, length = 20)
    private String status;  // ACTIVE / BLOCKED 등

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
