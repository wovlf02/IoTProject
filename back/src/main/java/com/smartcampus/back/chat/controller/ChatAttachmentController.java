package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.dto.response.ChatAttachmentResponse;
import com.smartcampus.back.chat.service.ChatAttachmentService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ChatAttachmentController
 * <p>
 * 채팅방 내 파일(이미지, PDF 등) 업로드 및 다운로드를 처리하는 컨트롤러입니다.
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatAttachmentController {

    private final ChatAttachmentService chatAttachmentService;

    /**
     * 채팅방 파일 첨부 업로드
     *
     * @param roomId 업로드할 채팅방 ID
     * @param file 업로드할 파일 (Multipart)
     * @return 업로드된 파일 정보
     */
    @PostMapping("/rooms/{roomId}/attachments")
    public ResponseEntity<ApiResponse<ChatAttachmentResponse>> uploadAttachment(
            @PathVariable Long roomId,
            @RequestPart("file") MultipartFile file) {

        ChatAttachmentResponse response = chatAttachmentService.uploadAttachment(roomId, file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 채팅방 파일 다운로드 (or 미리보기)
     *
     * @param attachmentId 첨부파일 ID
     * @return 파일 리소스 (Resource 타입)
     */
    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long attachmentId) {

        Resource resource = chatAttachmentService.downloadAttachment(attachmentId);
        return ResponseEntity.ok()
                .body(resource);
    }
}
