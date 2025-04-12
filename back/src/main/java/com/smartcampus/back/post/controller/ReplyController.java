package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.reply.ReplyCreateRequest;
import com.smartcampus.back.post.dto.reply.ReplyResponse;
import com.smartcampus.back.post.dto.reply.ReplyUpdateRequest;
import com.smartcampus.back.post.service.ReplyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments/{commentId}/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 대댓글 작성 + 첨부파일 업로드
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReplyResponse> createReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestPart("reply") ReplyCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        ReplyResponse response = replyService.createReply(postId, commentId, request, files);
        return ResponseEntity.ok(response);
    }

    /**
     * 대댓글 수정 + 파일 추가 가능
     */
    @PutMapping(value = "/{replyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReplyResponse> updateReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @PathVariable Long replyId,
            @RequestPart("reply") ReplyUpdateRequest request,
            @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles
    ) {
        ReplyResponse response = replyService.updateReply(postId, commentId, replyId, request, newFiles);
        return ResponseEntity.ok(response);
    }

    /**
     * 대댓글 삭제
     */
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @PathVariable Long replyId
    ) {
        replyService.deleteReply(postId, commentId, replyId);
        return ResponseEntity.ok("대댓글이 성공적으로 삭제되었습니다.");
    }

    /**
     * 대댓글 첨부파일 다운로드
     */
    @GetMapping("/{replyId}/attachments/{fileId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long replyId,
            @PathVariable Long fileId
    ) throws IOException {
        Resource resource = replyService.loadAttachmentFile(replyId, fileId);
        String filename = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
