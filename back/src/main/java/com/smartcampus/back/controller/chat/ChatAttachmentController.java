package com.smartcampus.back.controller.chat;

import com.smartcampus.back.dto.chat.response.ChatFilePreviewResponse;
import com.smartcampus.back.dto.chat.response.ChatMessageResponse;
import com.smartcampus.back.repository.chat.ChatMessageRepository;
import com.smartcampus.back.repository.chat.ChatRoomRepository;
import com.smartcampus.back.service.chat.ChatAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/chat/messages")
@RequiredArgsConstructor
public class ChatAttachmentController {

    private final ChatAttachmentService chatAttachmentService;
    private static final String UPLOAD_DIR = "C:/upload";
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * 채팅 메시지에 첨부된 파일 다운로드
     *
     * @param messageId 첨부파일이 포함된 메시지 ID
     * @return 바이너리 파일 다운로드 응답
     */
    @GetMapping("/{messageId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long messageId) {
        Resource fileResource = chatAttachmentService.loadFileAsResource(messageId);
        String filename = fileResource.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(fileResource);
    }

    /**
     * 첨부 이미지 파일 미리보기 스트리밍
     *
     * @param messageId 파일이 포함된 메시지 ID
     * @return 이미지 미리보기용 응답 (base64 or stream)
     */
    @GetMapping("/{messageId}/preview")
    public ResponseEntity<ChatFilePreviewResponse> previewImage(@PathVariable Long messageId) {
        ChatFilePreviewResponse preview = chatAttachmentService.previewFile(messageId);
        return ResponseEntity.ok(preview);
    }


    /**
     * 채팅방에 파일 메시지 업로드
     *
     * @param roomId    채팅방 ID
     * @param file      첨부할 파일
     * @param senderId  메시지 전송자 ID
     * @return 저장된 메시지 정보
     */
    @PostMapping("/{roomId}/upload")
    public ResponseEntity<ChatMessageResponse> uploadFileMessage(
            @PathVariable Long roomId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("senderId") Long senderId
    ) {
        ChatMessageResponse response = chatAttachmentService.saveFileMessage(roomId, senderId, file);
        return ResponseEntity.ok(response);
    }
}
