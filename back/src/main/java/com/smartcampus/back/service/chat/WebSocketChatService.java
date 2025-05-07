package com.smartcampus.back.service.chat;

import com.smartcampus.back.dto.chat.request.ChatMessageSendRequest;
import com.smartcampus.back.dto.chat.response.ChatMessageResponse;
import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatMessageRead;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.global.exception.NotFoundException;
import com.smartcampus.back.repository.ChatMessageReadRepository;
import com.smartcampus.back.repository.ChatMessageRepository;
import com.smartcampus.back.repository.ChatRoomRepository;
import com.smartcampus.back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * WebSocket을 통해 수신된 채팅 메시지를 처리하고
 * DB에 저장 및 응답을 구성하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class WebSocketChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageReadRepository chatMessageReadRepository;
    private final UserRepository userRepository;

    /**
     * 클라이언트로부터 받은 채팅 메시지를 처리합니다.
     *
     * @param request  메시지 전송 요청 DTO
     * @param senderId 메시지를 보낸 사용자 ID (JWT에서 추출)
     * @return 저장된 메시지를 기반으로 생성된 응답 DTO
     */
    @Transactional
    public ChatMessageResponse handleMessage(ChatMessageSendRequest request, Long senderId) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        // 메시지 생성 및 저장
        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .message(request.getMessage())
                .type(request.getType())
                .fileUrl(request.getFileUrl())
                .sentAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(message);

        // 읽음 상태 저장 (자기 자신은 읽음 처리)
        ChatMessageRead read = ChatMessageRead.builder()
                .chatMessage(message)
                .user(sender)
                .readAt(LocalDateTime.now())
                .build();
        chatMessageReadRepository.save(read);

        return ChatMessageResponse.from(message, true);
    }
}
