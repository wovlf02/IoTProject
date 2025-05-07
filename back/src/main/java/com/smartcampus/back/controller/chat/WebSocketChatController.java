package com.smartcampus.back.controller.chat;

import com.smartcampus.back.dto.chat.request.ChatMessageSendRequest;
import com.smartcampus.back.dto.chat.response.ChatMessageResponse;
import com.smartcampus.back.global.jwt.JwtProvider;
import com.smartcampus.back.service.chat.WebSocketChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * STOMP 기반 실시간 채팅 WebSocket 컨트롤러입니다.
 * 클라이언트로부터 메시지를 받고, DB 저장 후 구독 중인 모든 사용자에게 브로드캐스트합니다.
 */
@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketChatService chatService;
    private final JwtProvider jwtProvider;

    /**
     * 클라이언트 → 서버: 메시지 수신 엔드포인트
     * 클라이언트는 /pub/chat/message 로 메시지를 전송
     */
    @MessageMapping("/chat/message")
    public void handleMessage(
            @Valid @Payload ChatMessageSendRequest request,
            @Header("Authorization") String authHeader,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // JWT 추출 및 검증
        Long senderId = jwtProvider.getUserIdFromToken(resolveToken(authHeader));

        // 메시지 저장 및 응답 DTO 생성
        ChatMessageResponse response = chatService.handleMessage(request, senderId);

        // 서버 → 구독 클라이언트: 메시지 전파
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("유효한 Authorization 헤더가 필요합니다.");
    }
}
