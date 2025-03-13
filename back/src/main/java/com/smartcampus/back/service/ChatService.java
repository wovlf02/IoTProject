package com.smartcampus.back.service;

import com.smartcampus.back.dto.*;
import com.smartcampus.back.entity.ChatMember;
import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 채팅방 생성
     * @param request 채팅방 생성 요청 DTO
     * @return 생성된 채팅방 정보
     */
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

    /**
     * 사용자가 참여 중인 채팅방 목록 조회
     * @param userId 사용자 ID
     * @return 채팅방 목록
     */
    public List<ChatRoomResponse> getChatRooms(Long userId) {
        List<ChatMember> chatRooms = chatMemberRepository.findByUserId(userId);
        return chatRooms.stream()
                .map(chatMember -> new ChatRoomResponse(chatMember.getChatRoom()))
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 초대
     * @param chatId 채팅방 ID
     * @param request 초대 요청 DTO
     */
    @Transactional
    public void inviteUsers(Long chatId, InviteRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        for(Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            chatMemberRepository.save(new ChatMember(chatRoom, user, "member"));
        }
    }

    /**
     * 채팅방 나가기
     * @param chatId 채팅방 ID
     * @param userId 사용자 ID
     */
    @Transactional
    public void leaveChatRoom(Long chatId, Long userId) {
        ChatMember chatMember = chatMemberRepository.findByChatRoomAndUserId(chatId, usrId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 멤버를 찾을 수 없습니다."));

        chatMemberRepository.delete(chatMember);
    }

    // ========================= 2. 채팅 메시지 관리 ===========================

    @Transactional
    public ChatMessageResponse sendMessage(Long chatId, ChatMessageRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(request.getContent())
                .fileData(request.getFileData())
                .build();

        chatMessageRepository.save(message);

        return new ChatMessageResponse(message);
    }
}
