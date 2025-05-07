package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "target_type", "target_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq_gen")
    @SequenceGenerator(name = "like_seq_gen", sequenceName = "like_seq", allocationSize = 1)
    private Long id;

    @Column(name = "target_type", nullable = false, length = 30)
    private String targetType;  // 예: POST, COMMENT

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 좋아요 누른 사용자
}
