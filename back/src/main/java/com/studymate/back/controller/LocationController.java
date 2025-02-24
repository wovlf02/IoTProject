package com.studymate.back.controller;

import com.studymate.back.dto.LocationRequest;
import com.studymate.back.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // CORS 허용
public class LocationController {

    /**
     * 현재 위치 저장 API
     * @param request LocationRequest (요청 DTO)
     * @return LocationResponse (응답 DTO)
     */
    @PostMapping("/current")
    public ResponseEntity<LocationResponse> saveCurrentLocation(@RequestBody LocationRequest request) {
        try {
            // 요청된 위도/경도를 기반으로 응답 생성
            LocationResponse response = new LocationResponse(request.getLatitude(), request.getLongitude());

            System.out.println("📌 [백엔드] 요청 받음 -> 위도: " + request.getLatitude() + ", 경도: " + request.getLongitude());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new LocationResponse(null, null));
        }
    }
}
