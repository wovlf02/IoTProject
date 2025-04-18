package com.smartcampus.back.notification.service;

import com.google.firebase.messaging.*;
import com.smartcampus.back.notification.dto.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * FCM 서버를 통해 실제 알림을 전송하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FcmPushService {

    /**
     * 단일 사용자에게 알림 전송
     *
     * @param request 알림 전송 요청 DTO
     */
    public void sendNotification(NotificationSendRequest request) {
        try {
            // 메시지 생성
            Message message = Message.builder()
                    .setToken(getUserFcmToken(request.getReceiverId()))
                    .setNotification(Notification.builder()
                            .setTitle(request.getTitle())
                            .setBody(request.getBody())
                            .build()
                    )
                    .putData("type", request.getType() != null ? request.getType() : "")
                    .putData("referenceId", request.getReferenceId() != null ? request.getReferenceId().toString() : "")
                    .putData("clickAction", request.getClickAction() != null ? request.getClickAction() : "")
                    .build();

            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ FCM 전송 완료: {}", response);

        } catch (FirebaseMessagingException e) {
            log.error("❌ FCM 전송 실패: {}", e.getMessage(), e);
            throw new RuntimeException("FCM 전송 중 오류가 발생했습니다.");
        }
    }

    /**
     * 사용자 ID로부터 해당 사용자의 FCM 토큰을 조회합니다.
     * (※ 실제 구현에서는 DB 또는 Redis 등에서 토큰 조회)
     *
     * @param userId 사용자 ID
     * @return FCM 디바이스 토큰
     */
    private String getUserFcmToken(Long userId) {
        // 🔧 예시 구현 — 실제로는 유저 DB 또는 Redis 등에 저장된 FCM 토큰 조회 필요
        // ex) return fcmTokenRepository.findByUserId(userId).getToken();
        return "USER_FCM_TOKEN_PLACEHOLDER";
    }
}
