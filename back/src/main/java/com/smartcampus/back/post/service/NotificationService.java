package com.smartcampus.back.post.service;

import com.google.firebase.messaging.*;
import com.smartcampus.back.post.dto.notification.NotificationResponse;
import com.smartcampus.back.post.entity.Notification;
import com.smartcampus.back.post.exception.NotificationNotFoundException;
import com.smartcampus.back.post.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Firebase Cloud Messaging 기반의 알림 전송 및 알림 로그 저장 서비스
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * 단일 사용자에게 FCM 푸시 알림을 전송
     *
     * @param targetToken FCM 디바이스 토큰
     * @param title       알림 제목
     * @param body        알림 내용
     */
    public void sendNotificationToToken(String targetToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ 알림 전송 성공");
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ 알림 전송 실패: " + e.getMessage());
            throw new RuntimeException("알림 전송 중 오류 발생", e);
        }
    }

    /**
     * 다중 사용자에게 FCM 푸시 알림을 전송 (최대 500명)
     *
     * @param tokens 수신자들의 FCM 토큰 목록
     * @param title  알림 제목
     * @param body   알림 내용
     */
    public void sendNotificationToMultipleTokens(List<String> tokens, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.printf("✅ 다중 알림 전송 성공 (성공: %d, 실패: %d)%n",
                    response.getSuccessCount(), response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ 다중 알림 전송 실패: " + e.getMessage());
            throw new RuntimeException("다중 알림 전송 중 오류 발생", e);
        }
    }

    /**
     * 알림 로그 저장
     *
     * @param userId 수신자 ID
     * @param title  알림 제목
     * @param body   알림 내용
     */
    public void saveNotificationLog(Long userId, String title, String body) {
        Notification log = Notification.builder()
                .userId(userId)
                .title(title)
                .body(body)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(log);
    }

    /**
     * 특정 사용자의 전체 알림 목록 조회
     *
     * @param userId 사용자 ID
     * @return 알림 응답 DTO 리스트
     */
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .body(n.getBody())
                        .read(n.isRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 알림 읽음 처리
     *
     * @param notificationId 알림 ID
     */
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("해당 알림을 찾을 수 없습니다."));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * 알림 삭제
     *
     * @param notificationId 알림 ID
     */
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("해당 알림을 찾을 수 없습니다."));
        notificationRepository.delete(notification);
    }

    /**
     * 특정 사용자의 읽지 않은 알림 수 조회
     *
     * @param userId 사용자 ID
     * @return 읽지 않은 알림 수
     */
    public long countUnreadNotifications(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    /**
     * 사용자의 모든 알림을 읽음 처리
     *
     * @param userId 사용자 ID
     */
    public void readAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalse(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
