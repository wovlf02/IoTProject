package com.smartcampus.back.notification.controller;

import com.smartcampus.back.notification.dto.request.NotificationDeleteRequest;
import com.smartcampus.back.notification.dto.request.NotificationReadRequest;
import com.smartcampus.back.notification.dto.response.NotificationDetailResponse;
import com.smartcampus.back.notification.dto.response.NotificationResponse;
import com.smartcampus.back.global.dto.response.ApiResponse;
import com.smartcampus.back.notification.service.NotificationService;
import com.smartcampus.back.notification.entity.enums.NotificationType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * NotificationController
 * <p>
 * 알림 조회, 읽음 처리, 삭제 기능을 제공하는 컨트롤러입니다.
 * </p>
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 전체 알림 목록 조회 (Cursor 기반 무한 스크롤, 타입 필터링 가능)
     *
     * @param cursor 마지막으로 조회한 알림 ID
     * @param size 페이지 크기
     * @param type 알림 타입 필터 (optional)
     * @return 알림 목록 Slice
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Slice<NotificationResponse>>> getNotifications(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) NotificationType type
    ) {
        Slice<NotificationResponse> notifications = notificationService.getNotifications(cursor, size, type);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    /**
     * 알림 단건 상세 조회
     *
     * @param notificationId 알림 ID
     * @return 알림 상세 정보
     */
    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<NotificationDetailResponse>> getNotificationDetail(
            @PathVariable Long notificationId
    ) {
        NotificationDetailResponse response = notificationService.getNotificationDetail(notificationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 알림 단건 삭제
     *
     * @param notificationId 알림 ID
     * @return 성공 응답
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long notificationId
    ) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 알림 여러 개 삭제
     *
     * @param request 삭제할 알림 ID 리스트
     * @return 성공 응답
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteNotifications(
            @RequestBody @Valid NotificationDeleteRequest request
    ) {
        notificationService.deleteNotifications(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 알림 단건 읽음 처리
     *
     * @param notificationId 알림 ID
     * @return 성공 응답
     */
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> readNotification(
            @PathVariable Long notificationId
    ) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 알림 여러 개 읽음 처리
     *
     * @param request 읽음 처리할 알림 ID 리스트
     * @return 성공 응답
     */
    @PatchMapping("/read")
    public ResponseEntity<ApiResponse<Void>> readNotifications(
            @RequestBody @Valid NotificationReadRequest request
    ) {
        notificationService.readNotifications(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 전체 알림 모두 읽음 처리
     *
     * @return 성공 응답
     */
    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> readAllNotifications() {
        notificationService.readAllNotifications();
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 읽지 않은 알림 개수 조회
     *
     * @return 읽지 않은 알림 수
     */
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadNotificationCount() {
        Long unreadCount = notificationService.getUnreadNotificationCount();
        return ResponseEntity.ok(ApiResponse.success(unreadCount));
    }
}
