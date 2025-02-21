package com.studymate.back.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 위치 응답 DTO (서버 -> 클라이언트)
 * 서버가 클라이언트에게 위치 데이터를 반환할 때 사용
 */
@Getter
@Setter
public class LocationResponse {
    private Long id;    // 위치 데이터 ID
    private double latitude;    // 위도
    private double longitude;   // 경도
    private double accuracy;    // GPS 신호 정확도
    private boolean gpsEnabled; // GPS 활성화 여부
    private boolean networkAvailable;   // 네트워크 연결 상태
    private LocalDateTime timestamp;    // 데이터 생성 시간 (createdAt)
}
