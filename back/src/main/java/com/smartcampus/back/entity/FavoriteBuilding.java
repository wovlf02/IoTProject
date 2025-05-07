package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "favorite_buildings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "building_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fav_building_seq_gen")
    @SequenceGenerator(name = "fav_building_seq_gen", sequenceName = "fav_building_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
}
