package com.smartcampus.back.post.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Firebase Cloud Messaging(FCM)을 통해 푸시 알림을 전송하는 서비스입니다.
 * 단일 사용자에게 알림을 전송하며, 추후 다중 사용자/토픽 확장도 고려할 수 있습니다.
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    /**
     * 단일 디바이스 토큰을 가진 사용자에게 푸시 알림을 전송합니다.
     *
     * @param targetToken 알림을 받을 디바이스의 FCM 토큰
     * @param title       알림 제목
     * @param body        알림 내용
     */
    public void sendNotificationToToken(String targetToken, String title, String body) {
        // 1. FCM 알림 객체 구성
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // 2. FCM 메시지 객체 구성
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK") // 선택 사항
                .build();

        // 3. FCM 메시지 전송
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ FCM 알림 전송 성공: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ FCM 알림 전송 실패: " + e.getMessage());
            throw new RuntimeException("알림 전송 중 오류 발생", e);
        }
    }
}
