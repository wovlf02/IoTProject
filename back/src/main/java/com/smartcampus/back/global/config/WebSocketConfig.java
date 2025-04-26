package com.smartcampus.back.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket + STOMP 설정 클래스
 * <p>
 * 실시간 채팅을 위한 WebSocket 연결 및 메시지 브로커 경로를 설정합니다.
 * </p>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * WebSocket 엔드포인트 설정
     * <p>
     * 클라이언트는 해당 엔드포인트로 WebSocket을 연결합니다.
     * 예: ws://localhost:8080/ws/chat
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat") // WebSocket 연결 URI
                .setAllowedOriginPatterns("*") // CORS 허용 (실서버 도메인 제한 권장)
                .withSockJS(); // SockJS fallback (브라우저 호환용)
    }

    /**
     * 메시지 브로커 설정
     * <p>
     * 클라이언트가 pub/sub할 경로를 지정합니다.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // 서버가 클라이언트에게 보내는 주소 prefix
        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 서버로 보내는 주소 prefix
    }
}
