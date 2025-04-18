package com.smartcampus.back.notification.controller;

import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.notification.dto.request.NotificationSendRequest;
import com.smartcampus.back.notification.dto.response.NotificationListResponse;
import com.smartcampus.back.notification.dto.response.NotificationResponse;
import com.smartcampus.back.notification.service.NotificationService;
import com.smartcampus.back.notification.service.FcmPushService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FCM 알림 전송 및 조회를 위한 컨트롤러
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final FcmPushService fcmPushService;

    /**
     * 특정 사용자에게 FCM 알림을 전송합니다.
     *
     * @param request 알림 전송 요청 DTO
     * @return 전송 결과 메시지를 포함한 응답
     */
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendNotification(
            @Valid @RequestBody NotificationSendRequest request) {

        fcmPushService.sendNotification(request); // FCM 전송
        notificationService.saveNotification(request); // DB 저장

        return ResponseEntity.ok(ApiResponse.success("알림이 전송되었습니다."));
    }

    /**
     * 사용자의 모든 알림 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자의 알림 목록
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<NotificationListResponse>> getNotifications(
            @PathVariable Long userId) {

        NotificationListResponse response = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 단일 알림의 상세 정보를 조회합니다.
     *
     * @param notificationId 알림 ID
     * @return 알림 상세 정보
     */
    @GetMapping("/detail/{notificationId}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationDetail(
            @PathVariable Long notificationId) {

        NotificationResponse response = notificationService.getNotification(notificationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 단일 알림을 읽음 처리합니다.
     *
     * @param notificationId 알림 ID
     * @return 처리 결과 메시지
     */
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<String>> markAsRead(
            @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(ApiResponse.success("알림이 읽음 처리되었습니다."));
    }
}
