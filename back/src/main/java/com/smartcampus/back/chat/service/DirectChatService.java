package com.smartcampus.back.chat.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.chat.dto.request.DirectChatRequest;
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
 *
 * 친구/사용자 간 Direct 채팅방 생성 및 조회 기능 제공
 */
@Service
@RequiredArgsConstructor
public class DirectChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    private static final String ROOM_TYPE = "DIRECT";

    /**
     * 상대 userId 기준으로 1:1 채팅방 시작
     * (이미 존재 시 기존 반환, 없으면 생성)
     */
    public ChatRoomResponse startDirectChat(User requester, DirectChatRequest request, User targetUser) {
        // 1:1 채팅방 목록 중 두 사람이 모두 참가자로 등록된 방 찾기
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

        // 존재하지 않으면 새로 생성
        ChatRoom room = ChatRoom.builder()
                .title(requester.getNickname() + " ↔ " + targetUser.getNickname())
                .roomType(ROOM_TYPE)
                .refId(null) // direct는 연동 ID 없음
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(room);

        // 참가자 등록
        ChatParticipant requesterParticipant = ChatParticipant.builder()
                .chatRoom(savedRoom)
                .user(requester)
                .joinedAt(LocalDateTime.now())
                .active(true)
                .build();

        ChatParticipant targetParticipant = ChatParticipant.builder()
                .chatRoom(savedRoom)
                .user(targetUser)
                .joinedAt(LocalDateTime.now())
                .active(true)
                .build();

        chatParticipantRepository.saveAll(List.of(requesterParticipant, targetParticipant));

        return ChatRoomResponse.fromEntity(savedRoom);
    }

    /**
     * 현재 로그인 사용자가 속한 모든 1:1 채팅방 목록 조회
     */
    public List<ChatRoomResponse> getMyDirectChats(User user) {
        return chatParticipantRepository.findByUserAndActive(user, true).stream()
                .map(ChatParticipant::getChatRoom)
                .filter(room -> ROOM_TYPE.equals(room.getRoomType()))
                .map(ChatRoomResponse::fromEntity)
                .toList();
    }

    /**
     * 특정 사용자와의 1:1 채팅방 정보 조회 (없으면 예외)
     */
    public ChatRoomResponse getDirectRoomWith(User requester, User target) {
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

        throw new CustomException(ErrorCode.NOT_FOUND);
    }
}
