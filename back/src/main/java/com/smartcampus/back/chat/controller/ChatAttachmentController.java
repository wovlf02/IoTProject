package com.smartcampus.back.chat.controller;

import com.smartcampus.back.chat.service.ChatAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 채팅 메시지에 포함된 첨부파일 업로드 및 다운로드, 미리보기 API
 */
@RestController
@RequestMapping("/api/chat/messages")
@RequiredArgsConstructor
public class ChatAttachmentController {

    private final ChatAttachmentService chatAttachmentService;

    /**
     * 채팅 메시지에 첨부파일 업로드
     *
     * @param file MultipartFile
     * @param messageId 메시지 ID
     * @return 저장 완료 메시지
     */
    @PostMapping("/{messageId}/file")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long messageId) {
        chatAttachmentService.storeFile(file, messageId);
        return ResponseEntity.ok("파일이 업로드되었습니다.");
    }

    /**
     * 첨부파일 다운로드
     *
     * @param messageId 첨부파일이 연결된 메시지 ID
     * @return 파일 리소스 (application/octet-stream)
     */
    @GetMapping("/{messageId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long messageId) throws IOException {
        Resource resource = chatAttachmentService.loadFileAsResource(messageId);
        String fileName = resource.getFilename();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * 이미지 파일 미리보기
     *
     * @param fileId 첨부파일 ID
     * @return 이미지 리소스 스트림 (Content-Type = image/*)
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> previewImage(@PathVariable Long fileId) throws IOException {
        Resource image = chatAttachmentService.loadImageAsResource(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 또는 실제 확장자에 따라 동적으로 설정
                .body(image);
    }
}
