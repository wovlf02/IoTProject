package com.smartcampus.back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * WebSocket 및 STOMP 설정 클래스
 * <p>
 * 실시간 채팅 기능을 위한 WebSocket 엔드포인트 및 메시지 브로커 설정을 담당합니다.
 * </p>
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 클라이언트가 메시지를 전송할 prefix 설정 (예: /pub/chat/message)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 구간: 프론트엔드가 이 prefix로 시작하는 채널을 구독함
        registry.enableSimpleBroker("/sub");

        // 발행 구간: 클라이언트가 서버로 메시지를 전송할 때 사용하는 prefix
        registry.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * 클라이언트가 WebSocket 서버에 연결할 endpoint 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 프론트가 최초 연결할 endpoint
                .setAllowedOriginPatterns("*") // 개발 환경에서 모든 도메인 허용
                .withSockJS(); // SockJS fallback 지원
    }
}
