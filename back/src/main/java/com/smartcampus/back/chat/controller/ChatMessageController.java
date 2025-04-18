package com.smartcampus.back.chat.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.chat.dto.request.ChatMessageRequest;
import com.smartcampus.back.chat.dto.response.ChatMessageResponse;
import com.smartcampus.back.chat.service.ChatMessageService;
import com.smartcampus.back.common.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

/**
 * 채팅 메시지 REST & WebSocket 컨트롤러
 * <p>
 * WebSocket 메시지 송수신 및 채팅 기록 조회 기능 제공
 */
@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * WebSocket을 통해 채팅 메시지 전송
     *
     * @param request ChatMessageRequest (roomId, content 포함)
     * @param sender  현재 로그인한 사용자
     */
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageRequest request, @CurrentUser User sender) {
        // 메시지 저장
        chatMessageService.saveMessage(request, sender);

        // 구독자에게 메시지 브로드캐스트
        messagingTemplate.convertAndSend(
                "/sub/chat/room/" + request.getRoomId(),
                ChatMessageResponse.of(request, sender)
        );
    }

    /**
     * 채팅 메시지 조회 (무한 스크롤용)
     *
     * @param roomId 채팅방 ID
     * @param page   페이지 번호
     * @param size   한 페이지당 메시지 수
     * @return Page<ChatMessageResponse>
     */
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getMessages(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Page<ChatMessageResponse> response = chatMessageService.getMessages(roomId, page, size);
        return ResponseEntity.ok(response);
    }
}
