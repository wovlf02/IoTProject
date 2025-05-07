package com.smartcampus.back.service.chat;

import com.smartcampus.back.dto.chat.request.ChatInviteRequest;
import com.smartcampus.back.dto.chat.request.ChatRoomCreateRequest;
import com.smartcampus.back.dto.chat.response.*;
import com.smartcampus.back.entity.*;
import com.smartcampus.back.global.dto.response.CursorPageResponse;
import com.smartcampus.back.global.exception.NotFoundException;
import com.smartcampus.back.repository.*;
import com.smartcampus.back.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageReadRepository chatMessageReadRepository;
    private final UserRepository userRepository;

    /**
     * 채팅방 생성
     */
    public ChatRoomResponse createRoom(ChatRoomCreateRequest request) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(request.getRoomName())
                .isGroup(request.getGroup()) // 필드명 변경 반영
                .build();
        chatRoomRepository.save(chatRoom);

        Long myId = SecurityUtil.getCurrentUserId();
        chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, findUser(myId), "ADMIN"));

        if (Boolean.TRUE.equals(request.getGroup())) {
            // 그룹 채팅: 초대할 여러 사용자 ID
            for (Long userId : request.getUserIds()) {
                if (!userId.equals(myId)) {
                    chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, findUser(userId), "MEMBER"));
                }
            }
        } else {
            // 1:1 채팅: 단일 대상자
            Long targetId = request.getTargetUserId();
            if (targetId == null) {
                throw new IllegalArgumentException("1:1 채팅에는 대상 사용자 ID가 필요합니다.");
            }
            if (!targetId.equals(myId)) {
                chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, findUser(targetId), "MEMBER"));
            }
        }

        return ChatRoomResponse.from(chatRoom);
    }


    /**
     * 내가 속한 채팅방 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<ChatRoomSummaryResponse> getMyChatRooms() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ChatRoomMember> memberships = chatRoomMemberRepository.findByUserId(userId);

        return memberships.stream()
                .map(m -> {
                    Long roomId = m.getChatRoom().getId();
                    int unreadCount = (int) chatMessageReadRepository
                            .countByChatMessage_ChatRoom_IdAndUserIdAndReadAtIsNull(roomId, userId);
                    return ChatRoomSummaryResponse.from(m.getChatRoom(), unreadCount);
                })
                .toList();
    }


    /**
     * 채팅방 상세 조회
     */
    @Transactional(readOnly = true)
    public ChatRoomDetailResponse getRoomDetail(Long roomId) {
        ChatRoom chatRoom = findChatRoom(roomId);
        List<ChatRoomMember> members = chatRoomMemberRepository.findByChatRoomId(roomId);
        return ChatRoomDetailResponse.of(chatRoom, members);
    }

    /**
     * 채팅방 나가기
     */
    public void leaveRoom(Long roomId) {
        Long userId = SecurityUtil.getCurrentUserId();
        ChatRoomMember member = chatRoomMemberRepository.findByChatRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundException("채팅방에 속해있지 않습니다."));
        chatRoomMemberRepository.delete(member);
    }

    /**
     * 사용자 초대
     */
    public void inviteUser(Long roomId, ChatInviteRequest request) {
        ChatRoom chatRoom = findChatRoom(roomId);
        for (Long userId : request.getUserIds()) {
            if (!chatRoomMemberRepository.existsByChatRoomIdAndUserId(roomId, userId)) {
                chatRoomMemberRepository.save(ChatRoomMember.of(chatRoom, findUser(userId), "MEMBER"));
            }
        }
    }

    /**
     * 사용자 강퇴
     */
    public void kickUser(Long roomId, Long userId) {
        ChatRoomMember member = chatRoomMemberRepository.findByChatRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 채팅방에 존재하지 않습니다."));
        chatRoomMemberRepository.delete(member);
    }

    /**
     * 채팅방 멤버 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ChatMemberResponse> getRoomMembers(Long roomId) {
        return chatRoomMemberRepository.findByChatRoomId(roomId).stream()
                .map(ChatMemberResponse::from)
                .toList();
    }

    /**
     * 채팅 메시지 리스트 조회 (커서 기반)
     */
    @Transactional(readOnly = true)
    public CursorPageResponse<ChatMessageResponse> getMessages(Long roomId, Long cursor, int size) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ChatMessage> messages = chatMessageRepository
                .findByChatRoomIdOrderBySentAtDesc(roomId); // 필요 시 커서 로직 추가

        List<ChatMessageResponse> responseList = messages.stream()
                .map(m -> {
                    boolean isRead = chatMessageReadRepository
                            .findByChatMessageIdAndUserId(m.getId(), userId)
                            .isPresent();
                    return ChatMessageResponse.from(m, isRead);
                })
                .toList();

        return CursorPageResponse.of(responseList); // 커서 응답 객체 구성
    }


    /**
     * 채팅방별 안읽은 메시지 수 조회
     */
    @Transactional(readOnly = true)
    public List<UnreadCountResponse> getUnreadCounts() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ChatRoomMember> memberships = chatRoomMemberRepository.findByUserId(userId);

        return memberships.stream().map(m -> {
            long count = chatMessageReadRepository.countByChatMessage_ChatRoom_IdAndUserIdAndReadAtIsNull(
                    m.getChatRoom().getId(), userId);
            return new UnreadCountResponse(m.getChatRoom().getId(), count);
        }).toList();
    }

    // ===== 내부 유틸 =====

    private ChatRoom findChatRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("채팅방을 찾을 수 없습니다."));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }
}
