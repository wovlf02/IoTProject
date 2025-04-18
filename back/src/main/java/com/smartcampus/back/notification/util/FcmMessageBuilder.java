package com.smartcampus.back.notification.util;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.smartcampus.back.notification.dto.request.NotificationSendRequest;
import org.springframework.stereotype.Component;

/**
 * FCM 메시지 생성 유틸리티 클래스
 * <p>
 * NotificationSendRequest 기반으로 FCM 메시지를 구성합니다.
 * </p>
 */
@Component
public class FcmMessageBuilder {

    /**
     * FCM Message 객체 생성
     *
     * @param token   수신자의 FCM 디바이스 토큰
     * @param request 알림 요청 DTO
     * @return 전송 가능한 Message 객체
     */
    public Message buildMessage(String token, NotificationSendRequest request) {
        return Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build())
                .putData("type", request.getType() != null ? request.getType() : "")
                .putData("referenceId", request.getReferenceId() != null ? request.getReferenceId().toString() : "")
                .putData("clickAction", request.getClickAction() != null ? request.getClickAction() : "")
                .build();
    }
}
