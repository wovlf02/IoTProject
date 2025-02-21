package com.studymate.back.service;

import com.studymate.back.dto.LocationRequest;
import com.studymate.back.dto.LocationResponse;
import com.studymate.back.entity.Location;
import com.studymate.back.entity.User;
import com.studymate.back.repository.LocationRepository;
import com.studymate.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 위치 정보를 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    /**
     * 사용자의 위치 데이터를 저장하는 메서드
     * @param userId 사용자 고유 ID
     * @param request 요청 DTO
     * @return 프론트엔드에서 전송된 위치 정볼르 데이터베이스에 저장
     */
    @Transactional
    public LocationResponse saveLocation(Long userId, LocationRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Location location = Location.builder()
                .user(user)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .accuracy(request.getAccuracy())
                .gpsEnabled(request.isGpsEnabled())
                .networkAvailable(request.isNetworkAvailable())
                .build();

        locationRepository.save(location);
        return mapToLocationResponse(location);
    }

    /**
     * 특정 사용자의 최근 위치 데이터를 조회하는 메서드
     * @param userId 유저 고유 ID
     * @return 최근 위치 데이터
     */
    public List<LocationResponse> getUserLocations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return locationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToLocationResponse)
                .collect(Collectors.toList());
    }

    /**
     * Location Entity를 LocationResponse DTO로 변환하는 메서드
     * @param location Location Entity
     * @return LocationResponse DTO
     */
    private LocationResponse mapToLocationResponse(Location location) {
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setAccuracy(location.getAccuracy());
        response.setGpsEnabled(location.isGpsEnabled());
        response.setNetworkAvailable(location.isNetworkAvailable());
        response.setTimestamp(location.getCreatedAt());
        return response;
    }
}
