package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.notification.NotificationRequest;
import com.smartcampus.back.post.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Firebase 푸시 알림을 처리하는 컨트롤러
 * 단일 디바이스 토큰으로 알림을 전송
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 단일 사용자에게 FCM 푸시 알림을 전송합니다.
     *
     * @param request targetToken, title, body 정보를 포함한 요청 DTO
     * @return 성공 메시지
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotificationToToken(
                request.getTargetToken(),
                request.getTitle(),
                request.getBody()
        );
        return ResponseEntity.ok("✅ 알림이 성공적으로 전송되었습니다.");
    }
}
