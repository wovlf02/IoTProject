package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.request.DirectChatRequest;
import com.smartcampus.back.chat.dto.response.ChatRoomListResponse;
import com.smartcampus.back.chat.dto.response.ChatRoomResponse;
import com.smartcampus.back.chat.service.DirectChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 친구 및 사용자 간 1:1 채팅방 생성/조회 전용 컨트롤러
 */
@RestController
@RequestMapping("/api/chat/direct")
@RequiredArgsConstructor
public class DirectChatController {

    private final DirectChatService directChatService;

    /**
     * 특정 사용자와 1:1 채팅방 시작 (이미 존재하면 반환)
     *
     * @param request 상대 userId 포함 요청
     * @return 채팅방 응답 DTO
     */
    @PostMapping("/start")
    public ResponseEntity<ChatRoomResponse> startDirectChat(@RequestBody DirectChatRequest request) {
        ChatRoomResponse response = directChatService.startDirectChat(request.getUserId());
        return ResponseEntity.ok(response);
    }

    /**
     * 현재 로그인 사용자의 전체 1:1 채팅방 목록 조회
     *
     * @return 채팅방 리스트
     */
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomListResponse>> getMyDirectChatRooms() {
        List<ChatRoomListResponse> response = directChatService.getMyDirectChatRooms();
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 사용자와의 채팅방 단건 조회 (없으면 404)
     *
     * @param userId 상대 사용자 ID
     * @return 채팅방 응답
     */
    @GetMapping("/with/{userId}")
    public ResponseEntity<ChatRoomResponse> getDirectRoomWithUser(@PathVariable Long userId) {
        ChatRoomResponse response = directChatService.getRoomWithUser(userId);
        return ResponseEntity.ok(response);
    }
}
