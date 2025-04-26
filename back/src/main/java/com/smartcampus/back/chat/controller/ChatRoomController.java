package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.request.DirectChatRequest;
import com.smartcampus.back.chat.dto.request.ChatNotificationRequest;
import com.smartcampus.back.chat.dto.response.ChatRoomResponse;
import com.smartcampus.back.chat.dto.response.ChatRoomListResponse;
import com.smartcampus.back.chat.service.ChatRoomService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * ChatRoomController
 * <p>
 * 채팅방 관련 기능을 처리하는 REST API 컨트롤러입니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 내가 참여한 채팅방 목록 조회
     *
     * @return 내가 참여한 전체 채팅방 목록
     */
    @GetMapping
    public ApiResponse<List<ChatRoomListResponse>> getChatRooms() {
        List<ChatRoomListResponse> response = chatRoomService.getChatRooms();
        return ApiResponse.success(response);
    }

    /**
     * 채팅방 상세 조회
     *
     * @param roomId 채팅방 ID
     * @return 채팅방 상세 정보
     */
    @GetMapping("/{roomId}")
    public ApiResponse<ChatRoomResponse> getChatRoom(@PathVariable Long roomId) {
        ChatRoomResponse response = chatRoomService.getChatRoom(roomId);
        return ApiResponse.success(response);
    }

    /**
     * 1:1 채팅방 생성
     *
     * @param request 1:1 채팅방 생성 요청 정보
     * @return 생성된 채팅방 정보
     */
    @PostMapping
    public ApiResponse<ChatRoomResponse> createDirectChatRoom(@RequestBody @Valid DirectChatRequest request) {
        ChatRoomResponse response = chatRoomService.createDirectChatRoom(request);
        return ApiResponse.success(response);
    }

    /**
     * 채팅방 나가기
     *
     * @param roomId 채팅방 ID
     * @return 성공 여부
     */
    @DeleteMapping("/{roomId}/exit")
    public ApiResponse<Void> exitChatRoom(@PathVariable Long roomId) {
        chatRoomService.exitChatRoom(roomId);
        return ApiResponse.success();
    }

    /**
     * 채팅방 알림 설정 변경
     *
     * @param roomId 채팅방 ID
     * @param request 알림 설정 정보
     * @return 성공 여부
     */
    @PutMapping("/{roomId}/notifications")
    public ApiResponse<Void> changeNotificationSettings(@PathVariable Long roomId,
                                                        @RequestBody @Valid ChatNotificationRequest request) {
        chatRoomService.changeNotificationSettings(roomId, request);
        return ApiResponse.success();
    }
}
