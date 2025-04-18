package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.service.UserService;
import com.smartcampus.back.chat.dto.request.DirectChatRequest;
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

/**
 * 1:1 채팅 서비스
 * 친구/사용자 간 Direct 채팅방 생성 및 조회 기능 제공
 */
@Service
@RequiredArgsConstructor
public class DirectChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserService userService;

    private static final String ROOM_TYPE = "DIRECT";

    /**
     * 특정 사용자와 1:1 채팅 시작 또는 기존 채팅방 반환
     *
     * @param targetUserId 상대 사용자 ID
     * @return 채팅방 응답 DTO
     */
    public ChatRoomResponse startDirectChat(Long targetUserId) {
        User requester = userService.getCurrentUser(); // 현재 로그인 사용자
        User targetUser = userService.getUserById(targetUserId);

        // 두 유저가 참가자로 포함된 DIRECT 채팅방 존재 여부 확인
        List<ChatParticipant> myRooms = chatParticipantRepository.findByUserAndActive(requester, true);
        for (ChatParticipant p : myRooms) {
            ChatRoom room = p.getChatRoom();
            if (!ROOM_TYPE.equals(room.getRoomType())) continue;

            boolean exists = chatParticipantRepository
                    .existsByChatRoomIdAndUserId(room.getId(), targetUser.getId());
            if (exists) {
                return ChatRoomResponse.fromEntity(room);
            }
        }

        // 없으면 새로 생성
        ChatRoom room = ChatRoom.builder()
                .title(requester.getNickname() + " ↔ " + targetUser.getNickname())
                .roomType(ROOM_TYPE)
                .refId(null)
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(room);

        ChatParticipant p1 = ChatParticipant.builder()
                .chatRoom(savedRoom)
                .user(requester)
                .joinedAt(LocalDateTime.now())
                .active(true)
                .build();

        ChatParticipant p2 = ChatParticipant.builder()
                .chatRoom(savedRoom)
                .user(targetUser)
                .joinedAt(LocalDateTime.now())
                .active(true)
                .build();

        chatParticipantRepository.saveAll(List.of(p1, p2));

        return ChatRoomResponse.fromEntity(savedRoom);
    }

    /**
     * 로그인 사용자의 1:1 채팅방 전체 조회
     *
     * @return 채팅방 리스트
     */
    public List<ChatRoomListResponse> getMyDirectChatRooms() {
        User user = userService.getCurrentUser();

        return chatParticipantRepository.findByUserAndActive(user, true).stream()
                .map(ChatParticipant::getChatRoom)
                .filter(room -> ROOM_TYPE.equals(room.getRoomType()))
                .map(ChatRoomListResponse::fromEntity)
                .toList();
    }

    /**
     * 특정 사용자와의 채팅방 조회
     *
     * @param userId 상대 사용자 ID
     * @return 채팅방 응답 DTO
     */
    public ChatRoomResponse getRoomWithUser(Long userId) {
        User requester = userService.getCurrentUser();
        User target = userService.getUserById(userId);

        List<ChatParticipant> myRooms = chatParticipantRepository.findByUserAndActive(requester, true);
        for (ChatParticipant p : myRooms) {
            ChatRoom room = p.getChatRoom();
            if (!ROOM_TYPE.equals(room.getRoomType())) continue;

            boolean exists = chatParticipantRepository
                    .existsByChatRoomIdAndUserId(room.getId(), target.getId());
            if (exists) {
                return ChatRoomResponse.fromEntity(room);
            }
        }

        throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
    }
}
