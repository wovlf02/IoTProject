package com.smartcampus.back.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UNIVERSITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "university_seq_generator")
    @SequenceGenerator(name = "university_seq_generator", sequenceName = "UNIVERSITY_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name; // 학교 이름

    @Column(length = 500, nullable = false)
    private String address; // 학교 주소 (선택 사항)
}