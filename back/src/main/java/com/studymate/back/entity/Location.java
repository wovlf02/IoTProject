package com.studymate.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자의 위치 데이터를 저장하는 JPA Entity
 */
@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    /**
     * 위치 데이터의 고유 ID
     * Auto Increment -> sequence 사용
     * Oracle Express에서 sequence를 사용하여 ID 생성
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1)
    private Long id;

    /**
     * 사용자와의 관계 설정 (1:N)
     * 한 명의 사용자는 여러 개의 위치 데이터를 가질 수 있음
     * fetch = Fetch.LAZY를 사용하여 필요할 때만 조회 (성능 최적화)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 위도 (Latitude)
     * 필수 값 (Not Null)
     */
    @Column(nullable = false)
    private double latitude;

    /**
     * 경도 (Longitude)
     * 필수 값 (Not Null)
     */
    @Column(nullable = false)
    private double longitude;

    /**
     * GPS 신호 정확도
     * 값이 낮을수록 정확한 위치 데이터
     */
    @Column(nullable = false)
    private double accuracy;

    /**
     * GPS 활성화 상태 (true: 활성화 / false: 비활성화)
     * 사용자의 GPS가 활성화된 상태인지 확인
     */
    @Column(nullable = false)
    private boolean gpsEnabled;

    /**
     * 네트워크 연결 상태 (true: 연결됨, false: 끊김)
     * 위치 데이터 전송 시 네트워크 상태 저장
     */
    @Column(nullable = false)
    private boolean networkAvailable;

    /**
     * 위치 데이터가 저장된 시각 (createdAt)
     * Entity가 저장될 때 자동으로 설정됨
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Entity 저장 전 실행되는 메서드
     * createdAt을 현재 시간으로 자동 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
