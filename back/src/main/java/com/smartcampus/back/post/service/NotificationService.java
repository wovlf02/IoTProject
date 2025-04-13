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
 * Firebase Cloud Messaging 기반 알림 전송 및
 * 알림 로그의 생성, 조회, 수정, 삭제를 담당하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * FCM을 통해 단일 사용자에게 푸시 알림을 전송합니다.
     *
     * @param targetToken 수신자 디바이스의 FCM 토큰
     * @param title       알림 제목
     * @param body        알림 내용
     * @throws RuntimeException 알림 전송 중 문제가 발생할 경우 예외 발생
     */
    public void sendNotificationToToken(String targetToken, String title, String body) {
        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
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
            System.out.println("✅ FCM 단일 알림 전송 성공");
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ FCM 단일 알림 전송 실패: " + e.getMessage());
            throw new RuntimeException("FCM 단일 알림 전송 중 오류 발생", e);
        }
    }

    /**
     * FCM을 통해 최대 500명의 사용자에게 푸시 알림을 전송합니다.
     *
     * @param tokens 수신자의 FCM 토큰 리스트
     * @param title  알림 제목
     * @param body   알림 내용
     * @throws RuntimeException 알림 전송 중 문제가 발생할 경우 예외 발생
     */
    public void sendNotificationToMultipleTokens(List<String> tokens, String title, String body) {
        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
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
            System.out.printf("✅ FCM 다중 알림 전송 성공 (성공 %d건 / 실패 %d건)%n",
                    response.getSuccessCount(), response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ FCM 다중 알림 전송 실패: " + e.getMessage());
            throw new RuntimeException("FCM 다중 알림 전송 중 오류 발생", e);
        }
    }

    /**
     * 알림 로그를 데이터베이스에 저장합니다.
     *
     * @param userId 알림 수신자 ID
     * @param title  알림 제목
     * @param body   알림 본문
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
     * 특정 사용자의 알림 목록을 최신순으로 조회합니다.
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
     * 특정 알림을 읽음 처리합니다.
     *
     * @param notificationId 알림 ID
     * @throws NotificationNotFoundException 알림이 존재하지 않을 경우
     */
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("해당 알림을 찾을 수 없습니다."));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * 특정 알림을 삭제합니다.
     *
     * @param notificationId 알림 ID
     * @throws NotificationNotFoundException 알림이 존재하지 않을 경우
     */
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("해당 알림을 찾을 수 없습니다."));
        notificationRepository.delete(notification);
    }

    /**
     * 읽지 않은 알림의 개수를 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 읽지 않은 알림 수
     */
    public long countUnreadNotifications(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    /**
     * 해당 사용자의 모든 알림을 읽음 처리합니다.
     *
     * @param userId 사용자 ID
     */
    public void readAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalse(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
