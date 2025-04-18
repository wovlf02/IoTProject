package com.smartcampus.back.community.attachment.controller;

import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.community.attachment.dto.response.AttachmentListResponse;
import com.smartcampus.back.community.attachment.dto.response.AttachmentUploadResponse;
import com.smartcampus.back.community.attachment.service.AttachmentService;
import com.smartcampus.back.community.attachment.service.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 첨부파일 업로드, 조회, 다운로드를 처리하는 커뮤니티 API 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final FileStorageService fileStorageService;

    /**
     * 게시글에 첨부파일 업로드
     */
    @PostMapping("/posts/{postId}/attachments")
    public ApiResponse<AttachmentUploadResponse> uploadPostAttachments(
            @PathVariable Long postId,
            @RequestPart("files") List<MultipartFile> files
    ) throws IOException {
        return ApiResponse.success(attachmentService.uploadPostFiles(postId, files));
    }

    /**
     * 댓글에 첨부파일 업로드
     */
    @PostMapping("/comments/{commentId}/attachments")
    public ApiResponse<AttachmentUploadResponse> uploadCommentAttachments(
            @PathVariable Long commentId,
            @RequestPart("files") List<MultipartFile> files
    ) throws IOException {
        return ApiResponse.success(attachmentService.uploadCommentFiles(commentId, files));
    }

    /**
     * 대댓글에 첨부파일 업로드
     */
    @PostMapping("/replies/{replyId}/attachments")
    public ApiResponse<AttachmentUploadResponse> uploadReplyAttachments(
            @PathVariable Long replyId,
            @RequestPart("files") List<MultipartFile> files
    ) throws IOException {
        return ApiResponse.success(attachmentService.uploadReplyFiles(replyId, files));
    }

    /**
     * 게시글 첨부파일 목록 조회
     */
    @GetMapping("/posts/{postId}/attachments")
    public ApiResponse<AttachmentListResponse> getPostAttachments(@PathVariable Long postId) {
        return ApiResponse.success(attachmentService.getPostAttachments(postId));
    }

    /**
     * 댓글 첨부파일 목록 조회
     */
    @GetMapping("/comments/{commentId}/attachments")
    public ApiResponse<AttachmentListResponse> getCommentAttachments(@PathVariable Long commentId) {
        return ApiResponse.success(attachmentService.getCommentAttachments(commentId));
    }

    /**
     * 대댓글 첨부파일 목록 조회
     */
    @GetMapping("/replies/{replyId}/attachments")
    public ApiResponse<AttachmentListResponse> getReplyAttachments(@PathVariable Long replyId) {
        return ApiResponse.success(attachmentService.getReplyAttachments(replyId));
    }

    /**
     * 첨부파일 다운로드
     */
    @GetMapping("/attachments/{attachmentId}/download")
    public void downloadAttachment(
            @PathVariable Long attachmentId,
            HttpServletResponse response
    ) throws IOException {
        fileStorageService.downloadFile(attachmentId, response);
    }

    /**
     * 이미지 미리보기 (프론트 전용)
     */
    @GetMapping(value = "/attachments/{attachmentId}/preview", produces = MediaType.IMAGE_JPEG_VALUE)
    public void previewAttachment(
            @PathVariable Long attachmentId,
            HttpServletResponse response
    ) throws IOException {
        fileStorageService.previewImage(attachmentId, response);
    }

    /**
     * 첨부파일 삭제 (작성자 본인만 가능)
     */
    @DeleteMapping("/attachments/{attachmentId}")
    public ApiResponse<String> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ApiResponse.success("첨부파일이 삭제되었습니다.");
    }
}
