package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "buildings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"building_code"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_seq_gen")
    @SequenceGenerator(name = "building_seq_gen", sequenceName = "building_seq", allocationSize = 1)
    private Long id;

    @Column(name = "building_code", nullable = false, length = 50)
    private String buildingCode;  // 고유 건물 코드

    @Column(name = "building_name", nullable = false, length = 255)
    private String buildingName;

    @Column(name = "lat", nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @Column(name = "lng", nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "type", length = 50)
    private String type;  // 예: 강의동, 도서관 등
}
