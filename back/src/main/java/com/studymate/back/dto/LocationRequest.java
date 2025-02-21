package com.studymate.back.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 위치 요청 DTO (클라이언트 -> 서버)
 * 사용자가 현재 위치를 전송할 때 사용
 */
@Getter
@Setter
public class LocationRequest {
    private double latitude;    // 위도
    private double longitude;   // 경도
    private double accuracy;    // GPS 신호 정확도
    private boolean gpsEnabled; // GPS 활성화 여부
    private boolean networkAvailable;   // 네트워크 연결 상태
}
