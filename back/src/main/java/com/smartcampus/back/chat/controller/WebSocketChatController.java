package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.request.ChatMessageSendPayload;
import com.smartcampus.back.chat.dto.response.WebSocketMessageResponse;
import com.smartcampus.back.chat.service.ChatMessageService;
import com.smartcampus.back.chat.websocket.ChatMessagePublisher;
import com.smartcampus.back.chat.websocket.ChatSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * WebSocketChatController
 * <p>
 * WebSocket을 통해 실시간 채팅 메시지 송수신, 입장 및 퇴장 알림을 처리하는 컨트롤러입니다.
 * </p>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final ChatMessageService chatMessageService;
    private final ChatMessagePublisher chatMessagePublisher;
    private final ChatSessionManager chatSessionManager;

    /**
     * 클라이언트가 채팅방에 메시지를 보낼 때 호출
     *
     * @param message 메시지 내용 (웹소켓으로 받은 데이터)
     * @param headerAccessor 메시지 헤더 (채팅방 입장/퇴장 시 사용)
     */
    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageSendPayload message, SimpMessageHeaderAccessor headerAccessor) {
        Long roomId = message.getRoomId();
        Long senderId = (Long) headerAccessor.getSessionAttributes().get("userId");  // 세션에서 사용자 ID를 가져옴

        // 1. 메시지 저장
        WebSocketMessageResponse response = chatMessageService.sendMessage(roomId, message);

        // 2. 메시지 발행 (채팅방에 구독된 모든 클라이언트에게 메시지 전송)
        chatMessagePublisher.publish(roomId, response);

        log.info("[WebSocket] User [{}] sent message to Room [{}]", senderId, roomId);
    }

    /**
     * 사용자가 채팅방에 입장할 때 호출
     *
     * @param payload 채팅방 ID 정보
     * @param headerAccessor 메시지 헤더 (세션 관리)
     */
    @MessageMapping("/chat/enter")
    public void enterRoom(ChatMessageSendPayload payload, SimpMessageHeaderAccessor headerAccessor) {
        Long roomId = payload.getRoomId();
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");

        // 세션에 사용자 ID 추가 (채팅방 입장 시)
        chatSessionManager.enterRoom(roomId, userId);

        log.info("[WebSocket] User [{}] entered Room [{}]", userId, roomId);
    }

    /**
     * 사용자가 채팅방에서 퇴장할 때 호출
     *
     * @param payload 채팅방 ID 정보
     * @param headerAccessor 메시지 헤더 (세션 관리)
     */
    @MessageMapping("/chat/exit")
    public void exitRoom(ChatMessageSendPayload payload, SimpMessageHeaderAccessor headerAccessor) {
        Long roomId = payload.getRoomId();
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");

        // 세션에서 사용자 ID 제거 (채팅방 퇴장 시)
        chatSessionManager.exitRoom(roomId, userId);

        log.info("[WebSocket] User [{}] exited Room [{}]", userId, roomId);
    }
}
