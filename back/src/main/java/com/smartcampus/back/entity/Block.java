package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "blocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "target_type", "target_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "block_seq_gen")
    @SequenceGenerator(name = "block_seq_gen", sequenceName = "block_seq", allocationSize = 1)
    private Long id;

    @Column(name = "target_type", nullable = false, length = 30)
    private String targetType;  // 예: USER, POST, COMMENT

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 차단을 수행한 사용자
}
