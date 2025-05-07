package com.smartcampus.back.controller.chat;

import com.smartcampus.back.dto.chat.request.ChatInviteRequest;
import com.smartcampus.back.dto.chat.request.ChatRoomCreateRequest;
import com.smartcampus.back.dto.chat.response.*;
import com.smartcampus.back.global.dto.response.ApiResponse;
import com.smartcampus.back.global.dto.response.CursorPageResponse;
import com.smartcampus.back.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채팅방 관련 REST API를 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅방 생성 (1:1 또는 그룹)
     */
    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponse> createRoom(@RequestBody ChatRoomCreateRequest request) {
        return ApiResponse.success(chatService.createRoom(request));
    }

    /**
     * 채팅방 리스트 조회 (내가 속한 채팅방)
     */
    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomSummaryResponse>> getRoomList() {
        return ApiResponse.success(chatService.getMyChatRooms());
    }

    /**
     * 채팅방 상세 조회
     */
    @GetMapping("/rooms/{roomId}")
    public ApiResponse<ChatRoomDetailResponse> getRoomDetail(@PathVariable Long roomId) {
        return ApiResponse.success(chatService.getRoomDetail(roomId));
    }

    /**
     * 채팅방 나가기 (현재 로그인 사용자)
     */
    @DeleteMapping("/rooms/{roomId}/leave")
    public ApiResponse<Void> leaveRoom(@PathVariable Long roomId) {
        chatService.leaveRoom(roomId);
        return ApiResponse.success();
    }

    /**
     * 사용자 초대
     */
    @PostMapping("/rooms/{roomId}/invite")
    public ApiResponse<Void> inviteUser(@PathVariable Long roomId, @RequestBody ChatInviteRequest request) {
        chatService.inviteUser(roomId, request);
        return ApiResponse.success();
    }

    /**
     * 사용자 강퇴
     */
    @DeleteMapping("/rooms/{roomId}/kick/{userId}")
    public ApiResponse<Void> kickUser(@PathVariable Long roomId, @PathVariable Long userId) {
        chatService.kickUser(roomId, userId);
        return ApiResponse.success();
    }

    /**
     * 채팅방 멤버 목록 조회
     */
    @GetMapping("/rooms/{roomId}/members")
    public ApiResponse<List<ChatMemberResponse>> getRoomMembers(@PathVariable Long roomId) {
        return ApiResponse.success(chatService.getRoomMembers(roomId));
    }

    /**
     * 채팅 메시지 리스트 조회 (커서 기반)
     */
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<CursorPageResponse<ChatMessageResponse>> getMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ApiResponse.success(chatService.getMessages(roomId, cursor, size));
    }

    /**
     * 채팅방별 안읽은 메시지 수 조회
     */
    @GetMapping("/unread-counts")
    public ApiResponse<List<UnreadCountResponse>> getUnreadCounts() {
        return ApiResponse.success(chatService.getUnreadCounts());
    }
}
