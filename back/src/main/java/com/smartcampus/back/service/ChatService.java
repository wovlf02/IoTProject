package com.smartcampus.back.service;

import com.smartcampus.back.dto.*;
import com.smartcampus.back.entity.*;
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

    /**
     * 메시지 전송
     * @param chatId 채팅방 ID
     * @param request 메시지 요청 DTO
     * @return 전송된 메시지 정보
     */
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

    /**
     * 채팅 메시지 조회 (페이지네이션)
     * @param chatId 채팅방 ID
     * @return 채팅 메시지 목록
     */
    public List<ChatMessageResponse> getMessages(Long chatId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomId(chatId);
        return messages.stream()
                .map(ChatMessageResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 메시지 삭제
     * @param messageId 메시지 ID
     */
    @Transactional
    public void deleteMessage(Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        chatMessageRepository.delete(message);
    }

    /**
     * 메시지 읽음 처리
     * @param messageId 메시지 ID
     * @param userId 읽은 사용자 ID
     */
    @Transactional
    public void markMessageAsRead(Long messageId, Long userId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        chatReadReceiptRepository.save(new ChatReadReceipt(message, user));
    }

    // ========================= 3. 추가 기능 ===========================

    /**
     * 메시지 신고
     * @param messageId 신고할 메시지 ID
     * @param request 신고 요청 DTO
     */
    public void reportMessage(Long messageId, ReportRequest request) {
        chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        //신고 처리 로직
    }

    /**
     * 채팅방 투표 생성
     * @param chatId 채팅방 ID
     * @param request 투표 요청 DTO
     */
    public void createVote(Long chatId, VoteRequest request) {
        chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        // 투표 생성 로직
    }

    /**
     * 음성 메시지 전송
     * @param chatId 채팅방 ID
     * @param request 음성 메시지 요청 DTO
     */
    public void sendVoiceMessage(Long chatId, VoiceMessageRequest request) {
        chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        // 음성 메시지 전송 로직
    }
}
