package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.request.ChatMessageRequest;
import com.smartcampus.back.chat.dto.request.ChatMessageUpdateRequest;
import com.smartcampus.back.chat.dto.response.ChatMessageResponse;
import com.smartcampus.back.chat.service.ChatMessageService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import com.smartcampus.back.global.dto.response.CursorPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * ChatMessageController
 * <p>
 * 채팅방 내 메시지 전송, 수정, 삭제, 조회를 처리하는 REST API 컨트롤러입니다.
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * 채팅방 메시지 목록 조회 (Cursor 기반 무한 스크롤)
     *
     * @param roomId 채팅방 ID
     * @param cursor 커서 (null이면 최신부터)
     * @param size 페이지 크기
     * @return 메시지 목록
     */
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<CursorPageResponse<ChatMessageResponse>> getMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {

        CursorPageResponse<ChatMessageResponse> response = chatMessageService.getMessages(roomId, cursor, size);
        return ApiResponse.success(response);
    }

    /**
     * 채팅방에 메시지 전송 (REST 방식)
     *
     * @param roomId 채팅방 ID
     * @param request 메시지 내용
     * @return 저장된 메시지
     */
    @PostMapping("/rooms/{roomId}/messages")
    public ApiResponse<ChatMessageResponse> sendMessage(
            @PathVariable Long roomId,
            @RequestBody @Valid ChatMessageRequest request) {

        ChatMessageResponse response = chatMessageService.sendMessage(roomId, request);
        return ApiResponse.success(response);
    }

    /**
     * 메시지 내용 수정
     *
     * @param messageId 수정할 메시지 ID
     * @param request 수정할 메시지 내용
     * @return 수정된 메시지
     */
    @PatchMapping("/messages/{messageId}")
    public ApiResponse<ChatMessageResponse> updateMessage(
            @PathVariable Long messageId,
            @RequestBody @Valid ChatMessageUpdateRequest request) {

        ChatMessageResponse response = chatMessageService.updateMessage(messageId, request);
        return ApiResponse.success(response);
    }

    /**
     * 메시지 삭제 (soft delete)
     *
     * @param messageId 삭제할 메시지 ID
     * @return 성공 여부
     */
    @DeleteMapping("/messages/{messageId}")
    public ApiResponse<Void> deleteMessage(@PathVariable Long messageId) {
        chatMessageService.deleteMessage(messageId);
        return ApiResponse.success();
    }
}
