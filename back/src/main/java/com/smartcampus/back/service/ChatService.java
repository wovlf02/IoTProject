package com.smartcampus.back.service;

import com.smartcampus.back.dto.ChatRoomRequest;
import com.smartcampus.back.dto.ChatRoomResponse;
import com.smartcampus.back.entity.ChatMember;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채팅 서비스
 * 1:1 및 그룹 채팅 관리, 메시지 전송 및 관리 기능을 처리
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatReadReceiptRepository chatReadReceiptRepository;
    private final UserRepository userRepository;

    // ========================= 1. 채팅방 관리 ===========================

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest request) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(request.getRoomName())
                .isGroup(request.isGroup())
                .build();

        chatRoomRepository.save(chatRoom);

        // 채팅방 참여자 등록
        for (Long usrId : request.getParticipantIds()) {
            User user = userRepository.findById(usrId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            chatMemberRepository.save(new ChatMember(chatRoom, user, "member"));
        }

        return new ChatRoomResponse(chatRoom);
    }
}
