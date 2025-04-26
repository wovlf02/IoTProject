package com.smartcampus.back.chat.websocket;

import com.smartcampus.back.chat.dto.response.WebSocketMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * ChatMessagePublisher
 * <p>
 * WebSocket STOMP를 통해 채팅방 구독자에게 실시간 메시지를 발행하는 컴포넌트입니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ChatMessagePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅방 구독자에게 실시간 메시지를 발행합니다.
     *
     * @param roomId   메시지를 보낼 채팅방 ID
     * @param response 발행할 WebSocket 메시지 응답 객체
     */
    public void publish(Long roomId, WebSocketMessageResponse response) {
        String destination = "/sub/chat/rooms/" + roomId;
        messagingTemplate.convertAndSend(destination, response);
    }
}
