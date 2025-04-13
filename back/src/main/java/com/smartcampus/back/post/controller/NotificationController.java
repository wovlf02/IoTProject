package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.notification.NotificationRequest;
import com.smartcampus.back.post.dto.notification.MulticastNotificationRequest;
import com.smartcampus.back.post.dto.notification.NotificationResponse;
import com.smartcampus.back.post.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FCM 푸시 알림 전송 및 알림 로그 관련 기능을 제공하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 단일 사용자에게 FCM 푸시 알림을 전송합니다.
     * <p>실제 푸시 전송만 수행하며, DB에는 로그를 저장하지 않습니다.</p>
     *
     * @param request {@link NotificationRequest} targetToken, title, body 포함
     * @return 전송 결과 메시지
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotificationToToken(
                request.getTargetToken(),
                request.getTitle(),
                request.getBody()
        );
        return ResponseEntity.ok("✅ 단일 알림이 성공적으로 전송되었습니다.");
    }

    /**
     * 여러 사용자에게 FCM 푸시 알림을 전송합니다.
     * <p>최대 500명의 대상에게 동시에 전송되며, DB에는 로그를 저장하지 않습니다.</p>
     *
     * @param request {@link MulticastNotificationRequest} targetTokens, title, body 포함
     * @return 전송 결과 메시지
     */
    @PostMapping("/send-multiple")
    public ResponseEntity<String> sendNotificationToMultiple(@RequestBody MulticastNotificationRequest request) {
        notificationService.sendNotificationToMultipleTokens(
                request.getTargetTokens(),
                request.getTitle(),
                request.getBody()
        );
        return ResponseEntity.ok("✅ 다중 알림이 성공적으로 전송되었습니다.");
    }

    /**
     * 특정 사용자의 전체 알림 로그를 최신순으로 조회합니다.
     *
     * @param userId 알림을 조회할 사용자 ID
     * @return {@link NotificationResponse} 리스트
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    /**
     * 특정 알림을 '읽음' 상태로 변경합니다.
     *
     * @param notificationId 알림 ID
     * @return 읽음 처리 결과 메시지
     */
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("✅ 알림이 읽음 처리되었습니다.");
    }

    /**
     * 특정 알림을 삭제합니다.
     *
     * @param notificationId 삭제할 알림 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("✅ 알림이 삭제되었습니다.");
    }

    /**
     * 특정 사용자의 모든 알림을 읽음 상태로 일괄 변경합니다.
     *
     * @param userId 사용자 ID
     * @return 처리 결과 메시지
     */
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<String> readAllNotifications(@PathVariable Long userId) {
        notificationService.readAllNotifications(userId);
        return ResponseEntity.ok("✅ 전체 알림이 읽음 처리되었습니다.");
    }

    /**
     * 특정 사용자의 읽지 않은 알림 개수를 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 읽지 않은 알림 수
     */
    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(userId));
    }

    /**
     * 알림 로그만 DB에 저장합니다 (실제 푸시 전송 없이).
     * <p>내부 시스템 알림, 테스트, 사용자 메시지 보관 등에 사용됩니다.</p>
     *
     * @param userId 사용자 ID
     * @param title  알림 제목
     * @param body   알림 본문
     * @return 저장 성공 메시지
     */
    @PostMapping("/log")
    public ResponseEntity<String> saveNotificationLog(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String body
    ) {
        notificationService.saveNotificationLog(userId, title, body);
        return ResponseEntity.ok("✅ 알림 로그가 성공적으로 저장되었습니다.");
    }
}
