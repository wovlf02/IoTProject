package com.smartcampus.back.chat.service;

import com.smartcampus.back.chat.dto.request.ChatMessageSendPayload;
import com.smartcampus.back.chat.dto.response.WebSocketMessageResponse;
import com.smartcampus.back.chat.entity.ChatMessage;
import com.smartcampus.back.chat.repository.ChatMessageRepository;
import com.smartcampus.back.chat.websocket.ChatMessagePublisher;
import com.smartcampus.back.chat.websocket.ChatSessionManager;
import com.smartcampus.back.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * WebSocketChatService
 * <p>
 * WebSocket을 통한 실시간 메시지 처리와 세션 관리 기능을 담당하는 서비스입니다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WebSocketChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessagePublisher chatMessagePublisher;
    private final ChatSessionManager chatSessionManager;

    /**
     * 채팅 메시지 처리 (WebSocket 메시지 전송 및 DB 저장)
     *
     * @param payload  클라이언트에서 받은 메시지 데이터
     * @param userId   메시지를 보낸 사용자 ID
     * @return         WebSocket 응답 메시지
     */
    public WebSocketMessageResponse handleMessage(ChatMessageSendPayload payload, Long userId) {
        // 1. 메시지 저장
        ChatMessage savedMessage = saveMessage(payload, userId);

        // 2. WebSocket 메시지 응답 준비
        WebSocketMessageResponse response = WebSocketMessageResponse.fromEntity(savedMessage);

        // 3. 메시지 발행 (채팅방에 구독된 모든 클라이언트에게 실시간 전송)
        chatMessagePublisher.publish(payload.getRoomId(), response);

        log.info("[WebSocket] User [{}] sent message to Room [{}]", userId, payload.getRoomId());
        return response;
    }

    /**
     * 채팅방에 메시지 저장
     *
     * @param payload  클라이언트에서 받은 메시지 데이터
     * @param userId   메시지를 보낸 사용자 ID
     * @return         저장된 메시지
     */
    private ChatMessage saveMessage(ChatMessageSendPayload payload, Long userId) {
        // 메시지를 보낸 사용자 ID를 가져옴
        Long senderId = SecurityUtil.getCurrentUser().getId();

        // 메시지 객체 생성
        ChatMessage message = ChatMessage.builder()
                .roomId(payload.getRoomId())
                .sender(senderId)
                .messageType(payload.getMessageType())
                .content(payload.getContent())
                .attachmentId(payload.getAttachmentId())
                .build();

        // 메시지 저장
        return chatMessageRepository.save(message);
    }

    /**
     * 사용자가 채팅방에 입장할 때 호출 (세션 관리)
     *
     * @param payload 채팅방 정보
     * @param userId  입장하는 사용자 ID
     */
    public void handleEnterRoom(ChatMessageSendPayload payload, Long userId) {
        chatSessionManager.enterRoom(payload.getRoomId(), userId);
        log.info("[WebSocket] User [{}] entered Room [{}]", userId, payload.getRoomId());
    }

    /**
     * 사용자가 채팅방에서 퇴장할 때 호출 (세션 관리)
     *
     * @param payload 채팅방 정보
     * @param userId  퇴장하는 사용자 ID
     */
    public void handleExitRoom(ChatMessageSendPayload payload, Long userId) {
        chatSessionManager.exitRoom(payload.getRoomId(), userId);
        log.info("[WebSocket] User [{}] exited Room [{}]", userId, payload.getRoomId());
    }
}
