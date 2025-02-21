package com.studymate.back.controller;

import com.studymate.back.dto.LocationRequest;
import com.studymate.back.dto.LocationResponse;
import com.studymate.back.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 위치 데이터를 관리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    /**
     * 사용자의 위치 데이터를 저장하는 API
     * @param userId 사용자 고유 ID
     * @param request 사용자 위치 요청 DTO
     * @return 데이터베이스에 위치 데이터 저장
     */
    @PostMapping("/{userId}")
    public ResponseEntity<LocationResponse> saveLocation(@PathVariable Long userId, @RequestBody LocationRequest request) {
        return ResponseEntity.ok(locationService.saveLocation(userId, request));
    }

    /**
     * 특정 사용자의 최근 위치 목록을 조회하는 API
     * @param userId 사용자 고유 ID
     * @return 최근 위치 목록
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<LocationResponse>> getUserLocations(@PathVariable Long userId) {
        return ResponseEntity.ok(locationService.getUserLocations(userId));
    }
}
