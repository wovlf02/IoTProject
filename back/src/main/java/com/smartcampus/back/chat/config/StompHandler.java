package com.smartcampus.back.chat.config;

import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * STOMP CONNECT 요청 시 JWT 토큰을 검증하는 핸들러
 *
 * WebSocket 연결 단계에서만 수행됨
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * STOMP 프레임 인터셉트
     *
     * CONNECT 시 Authorization 헤더에서 토큰 추출 및 검증
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                throw new CustomException(ErrorCode.UNAUTHORIZED);  // 또는 WebSocketAuthException
            }

            String jwt = token.substring(7);
            if (!jwtTokenProvider.validateToken(jwt)) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }

            // 인증 성공 → 사용자 정보 추출
            Long userId = jwtTokenProvider.extractUserId(jwt);
            accessor.setUser(() -> String.valueOf(userId)); // Principal로 설정 (ChatController에서 사용 가능)
            log.info("WebSocket 연결 성공 - userId: {}", userId);
        }

        return message;
    }
}
