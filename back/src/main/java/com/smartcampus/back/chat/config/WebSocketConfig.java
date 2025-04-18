package com.smartcampus.back.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * WebSocket 설정 클래스
 *
 * STOMP 프로토콜 기반의 채팅 기능을 설정합니다.
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    /**
     * STOMP 엔드포인트 등록
     * 클라이언트는 이 URL로 WebSocket 연결을 시도합니다.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // 웹소켓 연결 경로
                .setAllowedOriginPatterns("*")  // CORS 허용 (배포 시 제한 필요)
                .withSockJS();                  // SockJS fallback 허용 (브라우저 호환성)
    }

    /**
     * 메시지 브로커 구성
     *
     * - /pub: 클라이언트가 서버로 메시지를 보낼 때 사용
     * - /sub: 서버가 클라이언트에게 메시지를 보낼 때 사용 (구독 기반)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");  // 내장 브로커 사용 (RabbitMQ 등으로 교체 가능)
    }

    /**
     * WebSocket 연결 시 인터셉터 설정
     *
     * STOMP CONNECT 시 JWT 인증을 처리하는 StompHandler 적용
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
