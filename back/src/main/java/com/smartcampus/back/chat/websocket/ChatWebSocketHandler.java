package com.smartcampus.back.chat.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * ChatWebSocketHandler
 * <p>
 * WebSocket 연결, 해제 이벤트를 감지하여 입장/퇴장 관리를 처리하는 핸들러입니다.
 * STOMP 기반 연결/종료 이벤트를 수신합니다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler {

    private final ChatSessionManager chatSessionManager;

    /**
     * WebSocket 연결 시 호출
     *
     * @param event 세션 연결 이벤트
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("[WebSocket] 연결됨, sessionId={}", accessor.getSessionId());
        // 입장 시점에서 특별한 처리는 하지 않는다 (구독 시 처리 예정)
    }

    /**
     * WebSocket 연결 해제 시 호출
     *
     * @param event 세션 해제 이벤트
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = accessor.getSessionId();
        log.info("[WebSocket] 연결 끊김, sessionId={}", sessionId);

        // 일반적으로 sessionId만 알 수 있기 때문에
        // 사용자-방 정보까지 관리하려면 따로 Session Store를 만들어야 함
        // 여기서는 간단히 로그만 출력
    }
}
