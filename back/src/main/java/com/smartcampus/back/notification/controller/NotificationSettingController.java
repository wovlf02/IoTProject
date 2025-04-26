package com.smartcampus.back.notification.controller;

import com.smartcampus.back.global.dto.response.ApiResponse;
import com.smartcampus.back.notification.dto.request.NotificationSettingRequest;
import com.smartcampus.back.notification.dto.response.NotificationSettingResponse;
import com.smartcampus.back.notification.service.NotificationSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * NotificationSettingController
 * <p>
 * 사용자의 알림 수신 설정(ON/OFF) 관리를 담당하는 컨트롤러입니다.
 * 알림 설정 조회 및 설정 변경 기능을 제공합니다.
 * </p>
 */
@RestController
@RequestMapping("/api/notifications/settings")
@RequiredArgsConstructor
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    /**
     * 알림 설정 전체 조회
     *
     * @return 사용자의 알림 설정 정보
     */
    @GetMapping
    public ResponseEntity<ApiResponse<NotificationSettingResponse>> getNotificationSettings() {
        NotificationSettingResponse settings = notificationSettingService.getNotificationSettings();
        return ResponseEntity.ok(ApiResponse.success(settings));
    }

    /**
     * 알림 설정 변경
     *
     * @param request 변경할 알림 설정 정보
     * @return 성공 응답
     */
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateNotificationSettings(
            @RequestBody @Valid NotificationSettingRequest request
    ) {
        notificationSettingService.updateNotificationSettings(request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
