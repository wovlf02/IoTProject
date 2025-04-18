package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.request.ChatJoinRequest;
import com.smartcampus.back.chat.dto.request.ChatRoomCreateRequest;
import com.smartcampus.back.chat.dto.response.ChatRoomListResponse;
import com.smartcampus.back.chat.dto.response.ChatRoomResponse;
import com.smartcampus.back.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채팅방 생성, 조회, 입장/퇴장 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     *
     * @param request 채팅방 생성 요청 DTO
     * @return 생성된 채팅방 응답
     */
    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(@RequestBody ChatRoomCreateRequest request) {
        ChatRoomResponse response = chatRoomService.createRoom(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 채팅방 목록 조회 (관리자 또는 공개방만 대상)
     *
     * @return 채팅방 목록 응답 리스트
     */
    @GetMapping
    public ResponseEntity<List<ChatRoomListResponse>> getAllRooms() {
        List<ChatRoomListResponse> response = chatRoomService.getAllRooms();
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 채팅방 정보 조회
     *
     * @param roomId 채팅방 ID
     * @return 채팅방 상세 정보 응답
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomResponse> getRoom(@PathVariable Long roomId) {
        ChatRoomResponse response = chatRoomService.getRoomById(roomId);
        return ResponseEntity.ok(response);
    }

    /**
     * 채팅방 입장 처리
     *
     * @param roomId 채팅방 ID
     * @param request 입장 요청 (userId 포함)
     * @return 입장 완료 메시지
     */
    @PostMapping("/{roomId}/join")
    public ResponseEntity<String> joinRoom(@PathVariable Long roomId, @RequestBody ChatJoinRequest request) {
        chatRoomService.joinRoom(roomId, request.getUserId());
        return ResponseEntity.ok("채팅방에 입장하였습니다.");
    }

    /**
     * 채팅방 퇴장 처리
     *
     * @param roomId 채팅방 ID
     * @param request 퇴장 요청 (userId 포함)
     * @return 퇴장 완료 메시지
     */
    @DeleteMapping("/{roomId}/exit")
    public ResponseEntity<String> exitRoom(@PathVariable Long roomId, @RequestBody ChatJoinRequest request) {
        chatRoomService.exitRoom(roomId, request.getUserId());
        return ResponseEntity.ok("채팅방에서 나갔습니다.");
    }

    /**
     * 비어 있는 채팅방 삭제 (자동 또는 수동 호출)
     *
     * @param roomId 채팅방 ID
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        chatRoomService.deleteRoomIfEmpty(roomId);
        return ResponseEntity.ok("채팅방이 삭제되었습니다.");
    }
}
