package com.smartcampus.back.notification.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Firebase Cloud Messaging (FCM) 푸시 알림 전송 유틸리티 클래스
 * <p>
 * 이 클래스는 사용자 디바이스 토큰을 기반으로 푸시 알림을 전송하는 기능을 제공합니다.
 * 서버 키(server key)는 application.yml에서 주입받아 사용합니다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FcmPushUtil {

    private static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fcm.server-key}")
    private String firebaseServerKey;

    /**
     * 단일 디바이스 토큰에 푸시 알림을 전송합니다.
     *
     * @param deviceToken 대상 디바이스 토큰
     * @param title       알림 제목
     * @param body        알림 본문
     */
    public void sendPushNotification(String deviceToken, String title, String body) {
        if (deviceToken == null || deviceToken.isBlank()) {
            log.warn("[FCM] 디바이스 토큰이 비어있어 푸시 전송을 생략합니다.");
            return;
        }

        // 요청 본문 생성
        String payload = buildPayload(deviceToken, title, body);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(firebaseServerKey);  // Authorization: Bearer {server key}

        HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);

        try {
            // POST 요청 전송
            ResponseEntity<String> response = restTemplate.postForEntity(FCM_API_URL, httpEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("[FCM] 푸시 알림 전송 성공: {}", response.getBody());
            } else {
                log.error("[FCM] 푸시 알림 전송 실패: 상태 코드 {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("[FCM] 푸시 알림 전송 중 예외 발생", e);
        }
    }

    /**
     * FCM API에 요청할 JSON 페이로드를 생성합니다.
     *
     * @param deviceToken 대상 디바이스 토큰
     * @param title       알림 제목
     * @param body        알림 본문
     * @return JSON 형태의 요청 문자열
     */
    private String buildPayload(String deviceToken, String title, String body) {
        return "{"
                + "\"to\": \"" + deviceToken + "\","
                + "\"notification\": {"
                + "\"title\": \"" + title + "\","
                + "\"body\": \"" + body + "\","
                + "\"sound\": \"default\""
                + "}"
                + "}";
    }
}
