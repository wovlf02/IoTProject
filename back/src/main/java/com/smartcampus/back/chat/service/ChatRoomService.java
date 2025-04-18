package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.chat.dto.request.ChatJoinRequest;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 채팅방 서비스
 *
 * 채팅방 생성, 조회, 입장/퇴장 등을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    /**
     * 채팅방 생성
     *
     * @param request 채팅방 생성 요청 DTO
     * @return ChatRoomResponse
     */
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
     * 채팅방 입장 (참가 등록)
     */
    public void joinRoom(Long roomId, User user) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        chatParticipantRepository.findByChatRoomAndUser(room, user)
                .ifPresentOrElse(
                        participant -> {
                            participant.setActive(true);
                            participant.setExitedAt(null);
                            chatParticipantRepository.save(participant);
                        },
                        () -> {
                            ChatParticipant participant = ChatParticipant.builder()
                                    .chatRoom(room)
                                    .user(user)
                                    .active(true)
                                    .joinedAt(LocalDateTime.now())
                                    .build();
                            chatParticipantRepository.save(participant);
                        }
                );
    }

    /**
     * 채팅방 퇴장 (참가 상태 false 처리)
     */
    public void exitRoom(Long roomId, User user) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        ChatParticipant participant = chatParticipantRepository
                .findByChatRoomAndUser(room, user)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        participant.setActive(false);
        participant.setExitedAt(LocalDateTime.now());
        chatParticipantRepository.save(participant);
    }

    /**
     * 채팅방 단건 조회
     */
    public ChatRoomResponse getRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return ChatRoomResponse.fromEntity(room);
    }

    /**
     * 전체 채팅방 목록 조회 (관리자 또는 공개된 방만)
     */
    public List<ChatRoomListResponse> getAllRooms() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return rooms.stream()
                .map(ChatRoomListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 삭제 (비어있거나 내부 조건 만족 시)
     */
    public void deleteRoom(Long roomId) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        chatRoomRepository.deleteById(roomId);
    }
}
