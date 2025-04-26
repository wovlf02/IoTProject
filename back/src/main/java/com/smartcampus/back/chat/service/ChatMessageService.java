package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.service.UserService;
import com.smartcampus.back.chat.dto.request.ChatMessageRequest;
import com.smartcampus.back.chat.dto.request.ChatMessageSendPayload;
import com.smartcampus.back.chat.dto.request.ChatMessageUpdateRequest;
import com.smartcampus.back.chat.dto.response.ChatMessageResponse;
import com.smartcampus.back.chat.entity.ChatMessage;
import com.smartcampus.back.chat.repository.ChatMessageRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.global.dto.response.CursorPageResponse;
import com.smartcampus.back.global.dto.response.CursorPageResponse.CursorResult;
import com.smartcampus.back.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ChatMessageService
 * <p>
 * 채팅 메시지의 저장, 수정, 삭제, 조회를 처리하는 서비스입니다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    /**
     * 채팅방에 메시지 전송 (WebSocket)
     *
     * @param senderId 보낸 사용자 ID
     * @param payload 보낼 메시지 내용
     * @return 저장된 메시지
     */
    public ChatMessage saveMessage(Long senderId, ChatMessageSendPayload payload) {
        User sender = userService.getUserById(senderId);

        ChatMessage message = ChatMessage.builder()
                .roomId(payload.getRoomId())
                .sender(sender)
                .content(payload.getContent())
                .attachmentId(payload.getAttachmentId())
                .messageType(payload.getMessageType())
                .build();

        return chatMessageRepository.save(message);
    }

    /**
     * 채팅방에 메시지 전송 (REST)
     *
     * @param roomId 채팅방 ID
     * @param request 전송할 메시지
     * @return 저장된 메시지 응답
     */
    public ChatMessageResponse sendMessage(Long roomId, ChatMessageRequest request) {
        User sender = SecurityUtil.getCurrentUser();

        ChatMessage message = ChatMessage.builder()
                .roomId(roomId)
                .sender(sender)
                .content(request.getContent())
                .attachmentId(request.getAttachmentId())
                .messageType(request.getMessageType())
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        return ChatMessageResponse.fromEntity(saved);
    }

    /**
     * 채팅방 메시지 목록 조회 (Cursor 기반 무한스크롤)
     *
     * @param roomId 채팅방 ID
     * @param cursor 커서 ID (null이면 최신부터)
     * @param size 페이지 크기
     * @return 메시지 리스트
     */
    @Transactional(readOnly = true)
    public CursorPageResponse<ChatMessageResponse> getMessages(Long roomId, Long cursor, int size) {
        List<ChatMessage> messages;
        if (cursor == null) {
            // 최신 메시지부터 size만큼
            messages = chatMessageRepository.findAllByRoomIdAndDeletedFalseOrderBySentAtAsc(roomId);
        } else {
            // cursor 이전 메시지 조회
            messages = chatMessageRepository.findByRoomIdAndDeletedFalseAndIdLessThanOrderBySentAtDesc(roomId, cursor);
        }

        // 페이징 후 nextCursor 설정
        Long nextCursor = messages.isEmpty() ? null : messages.get(messages.size() - 1).getId();
        List<ChatMessageResponse> result = messages.stream()
                .map(ChatMessageResponse::fromEntity)
                .toList();

        return CursorPageResponse.of(result, CursorResult.of(nextCursor, size));
    }

    /**
     * 채팅 메시지 수정
     *
     * @param messageId 수정할 메시지 ID
     * @param request 수정할 내용
     * @return 수정된 메시지
     */
    public ChatMessageResponse updateMessage(Long messageId, ChatMessageUpdateRequest request) {
        ChatMessage message = getMessageById(messageId);
        User currentUser = SecurityUtil.getCurrentUser();

        // 본인만 수정 가능
        if (!message.getSender().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if (message.getMessageType() != ChatMessage.MessageType.TEXT) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE); // 파일 메시지는 수정 불가
        }

        message.setContent(request.getContent());
        return ChatMessageResponse.fromEntity(message);
    }

    /**
     * 채팅 메시지 삭제 (soft delete)
     *
     * @param messageId 삭제할 메시지 ID
     */
    public void deleteMessage(Long messageId) {
        ChatMessage message = getMessageById(messageId);
        User currentUser = SecurityUtil.getCurrentUser();

        if (!message.getSender().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        message.setDeleted(true);
    }

    /**
     * 메시지 ID로 메시지 조회
     *
     * @param messageId 메시지 ID
     * @return 조회된 메시지
     */
    @Transactional(readOnly = true)
    public ChatMessage getMessageById(Long messageId) {
        return chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.MESSAGE_NOT_FOUND));
    }
}
