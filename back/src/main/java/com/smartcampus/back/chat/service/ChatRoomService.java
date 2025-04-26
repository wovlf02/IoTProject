package com.smartcampus.back.chat.service;

import com.smartcampus.back.chat.dto.request.DirectChatRequest;
import com.smartcampus.back.chat.dto.request.ChatNotificationRequest;
import com.smartcampus.back.chat.dto.response.ChatRoomResponse;
import com.smartcampus.back.chat.dto.response.ChatRoomListResponse;
import com.smartcampus.back.chat.entity.ChatRoom;
import com.smartcampus.back.chat.entity.ChatParticipant;
import com.smartcampus.back.chat.repository.ChatRoomRepository;
import com.smartcampus.back.chat.repository.ChatParticipantRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ChatRoomService
 * <p>
 * 채팅방 관련 기능을 처리하는 서비스입니다.
 * 채팅방 생성, 조회, 나가기, 알림 설정 등을 처리합니다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    /**
     * 내가 참여한 채팅방 목록 조회
     *
     * @return 내가 참여한 전체 채팅방 목록
     */
    public List<ChatRoomListResponse> getChatRooms() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        List<ChatRoom> rooms = chatRoomRepository.findByParticipantId(userId);

        return rooms.stream()
                .map(room -> new ChatRoomListResponse(room.getId(), room.getName(), room.getRoomType(), room.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 상세 조회
     *
     * @param roomId 채팅방 ID
     * @return 채팅방 상세 정보
     */
    public ChatRoomResponse getChatRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return new ChatRoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getCreatedAt());
    }

    /**
     * 1:1 채팅방 생성
     *
     * @param request 1:1 채팅방 생성 요청
     * @return 생성된 채팅방 정보
     */
    public ChatRoomResponse createDirectChatRoom(DirectChatRequest request) {
        Long currentUserId = SecurityUtil.getCurrentUser().getId();
        // 친구 요청자 (targetUserId)로 채팅방 생성
        ChatRoom room = ChatRoom.builder()
                .name("1:1 채팅방")
                .roomType(ChatRoom.RoomType.DIRECT)
                .build();

        chatRoomRepository.save(room);

        // 현재 사용자와 친구를 채팅방 참여자로 추가
        addParticipantToRoom(room.getId(), currentUserId);
        addParticipantToRoom(room.getId(), request.getTargetUserId());

        return new ChatRoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getCreatedAt());
    }

    /**
     * 채팅방 참여자 추가
     *
     * @param roomId 채팅방 ID
     * @param userId 사용자 ID
     */
    private void addParticipantToRoom(Long roomId, Long userId) {
        ChatParticipant participant = new ChatParticipant();
        participant.setRoomId(roomId);
        participant.setUserId(userId);
        chatParticipantRepository.save(participant);
    }

    /**
     * 채팅방 나가기
     *
     * @param roomId 채팅방 ID
     */
    public void exitChatRoom(Long roomId) {
        Long currentUserId = SecurityUtil.getCurrentUser().getId();
        // 채팅방 참여자 제거
        chatParticipantRepository.deleteByRoomIdAndUserId(roomId, currentUserId);

        log.info("[ChatRoom] User [{}] exited Room [{}]", currentUserId, roomId);
    }

    /**
     * 채팅방 알림 설정 변경
     *
     * @param roomId 채팅방 ID
     * @param request 알림 설정 정보
     */
    public void changeNotificationSettings(Long roomId, ChatNotificationRequest request) {
        // 여기서는 알림 설정 로직을 다루지만, 실제로 알림 상태는 별도 테이블로 관리하거나
        // 사용자의 개인 설정을 통해서 저장해야 할 수 있다.

        // 예시로 알림 ON/OFF를 로그로 처리
        log.info("[ChatRoom] Notifications for Room [{}] set to [{}]", roomId, request.isNotificationsEnabled());
    }
}
