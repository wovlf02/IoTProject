package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.service.UserService;
import com.smartcampus.back.chat.dto.request.ChatRoomCreateRequest;
import com.smartcampus.back.chat.dto.response.ChatRoomListResponse;
import com.smartcampus.back.chat.dto.response.ChatRoomResponse;
import com.smartcampus.back.chat.entity.ChatParticipant;
import com.smartcampus.back.chat.entity.ChatRoom;
import com.smartcampus.back.chat.repository.ChatParticipantRepository;
import com.smartcampus.back.chat.repository.ChatRoomRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 채팅방 서비스
 * 채팅방 생성, 조회, 입장/퇴장 등을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserService userService;

    /**
     * 채팅방 생성
     */
    @Transactional
    public ChatRoomResponse createRoom(ChatRoomCreateRequest request) {
        ChatRoom room = ChatRoom.builder()
                .title(request.getTitle())
                .roomType(request.getRoomType())
                .refId(request.getRefId())
                .build();

        ChatRoom saved = chatRoomRepository.save(room);
        return ChatRoomResponse.fromEntity(saved);
    }

    /**
     * 전체 채팅방 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ChatRoomListResponse> getAllRooms() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 단건 조회
     */
    @Transactional(readOnly = true)
    public ChatRoomResponse getRoomById(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        return ChatRoomResponse.fromEntity(room);
    }

    /**
     * 채팅방 입장 (사용자 참가 처리)
     */
    @Transactional
    public void joinRoom(Long roomId, Long userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        User user = userService.getUserById(userId);

        chatParticipantRepository.findByChatRoomAndUser(room, user)
                .ifPresentOrElse(
                        participant -> {
                            participant.setActive(true);
                            participant.setExitedAt(null);
                            chatParticipantRepository.save(participant);
                        },
                        () -> {
                            ChatParticipant newParticipant = ChatParticipant.builder()
                                    .chatRoom(room)
                                    .user(user)
                                    .joinedAt(LocalDateTime.now())
                                    .active(true)
                                    .build();
                            chatParticipantRepository.save(newParticipant);
                        }
                );
    }

    /**
     * 채팅방 퇴장 처리
     */
    @Transactional
    public void exitRoom(Long roomId, Long userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        User user = userService.getUserById(userId);

        ChatParticipant participant = chatParticipantRepository.findByChatRoomAndUser(room, user)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        participant.setActive(false);
        participant.setExitedAt(LocalDateTime.now());
        chatParticipantRepository.save(participant);
    }

    /**
     * 채팅방 삭제 (참여자 0명일 경우만 삭제)
     */
    @Transactional
    public void deleteRoomIfEmpty(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        boolean hasParticipants = chatParticipantRepository.existsByChatRoomAndActive(room, true);
        if (hasParticipants) {
            throw new CustomException(ErrorCode.FORBIDDEN); // 또는 custom 에러코드
        }

        chatRoomRepository.delete(room);
    }
}
