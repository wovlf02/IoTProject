package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.chat.dto.request.ChatMessageRequest;
import com.smartcampus.back.chat.dto.response.ChatMessageResponse;
import com.smartcampus.back.chat.entity.ChatMessage;
import com.smartcampus.back.chat.entity.ChatRoom;
import com.smartcampus.back.chat.repository.ChatMessageRepository;
import com.smartcampus.back.chat.repository.ChatRoomRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 채팅 메시지 서비스
 *
 * WebSocket 메시지 수신 시 DB 저장 및 기록 조회를 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅 메시지 저장
     *
     * @param request 메시지 요청 DTO
     * @param sender 보낸 사용자
     * @return 저장된 ChatMessage
     */
    public ChatMessage saveMessage(ChatMessageRequest request, User sender) {
        ChatRoom room = chatRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(room)
                .sender(sender)
                .content(request.getMessage())
                .hasFile(false)
                .build();

        return chatMessageRepository.save(message);
    }

    /**
     * 채팅 메시지 목록 조회 (최신순, 페이징)
     *
     * @param roomId 채팅방 ID
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return Page<ChatMessageResponse>
     */
    public Page<ChatMessageResponse> getMessages(Long roomId, int page, int size) {
        Page<ChatMessage> messages = chatMessageRepository
                .findByChatRoomIdOrderBySentAtDesc(roomId, PageRequest.of(page, size));

        return messages.map(ChatMessageResponse::fromEntity);
    }
}
