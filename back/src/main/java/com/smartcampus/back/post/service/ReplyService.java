package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.reply.ReplyCreateRequest;
import com.smartcampus.back.post.dto.reply.ReplyResponse;
import com.smartcampus.back.post.dto.reply.ReplyUpdateRequest;
import com.smartcampus.back.post.entity.*;
import com.smartcampus.back.post.enums.AttachmentTargetType;
import com.smartcampus.back.post.exception.*;
import com.smartcampus.back.post.repository.AttachmentRepository;
import com.smartcampus.back.post.repository.CommentRepository;
import com.smartcampus.back.post.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    /**
     * 대댓글 생성
     */
    public ReplyResponse createReply(Long postId, Long commentId, ReplyCreateRequest request, List<MultipartFile> files) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 댓글은 지정된 게시글에 속하지 않습니다.");
        }

        Reply reply = Reply.builder()
                .comment(comment)
                .writerId(request.getWriterId())
                .content(request.getContent())
                .build();

        Reply savedReply = replyRepository.save(reply);

        if (files != null && !files.isEmpty()) {
            List<Attachment> attachments = files.stream()
                    .map(file -> fileStorageService.storeFile(file, savedReply.getId(), AttachmentTargetType.REPLY))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(attachments);
        }

        return buildReplyResponse(savedReply);
    }

    /**
     * 대댓글 수정
     */
    public ReplyResponse updateReply(Long postId, Long commentId, Long replyId, ReplyUpdateRequest request, List<MultipartFile> newFiles) {
        Reply reply = replyRepository.findByIdAndCommentId(replyId, commentId);
        if (reply == null) throw new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다.");

        if (!reply.getComment().getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 대댓글은 지정된 게시글에 속하지 않습니다.");
        }

        if (!reply.getWriterId().equals(request.getWriterId())) {
            throw new UnauthorizedAccessException("해당 대댓글에 대한 수정 권한이 없습니다.");
        }

        reply.setContent(request.getContent());
        reply.setUpdatedAt(LocalDateTime.now());
        Reply updated = replyRepository.save(reply);

        if (newFiles != null && !newFiles.isEmpty()) {
            List<Attachment> attachments = newFiles.stream()
                    .map(f -> fileStorageService.storeFile(f, replyId, AttachmentTargetType.REPLY))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(attachments);
        }

        return buildReplyResponse(updated);
    }

    /**
     * 대댓글 삭제
     */
    public void deleteReply(Long postId, Long commentId, Long replyId, Long requesterId) {
        Reply reply = replyRepository.findByIdAndCommentId(replyId, commentId);
        if (reply == null) throw new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다.");

        if (!reply.getComment().getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 대댓글은 지정된 게시글에 속하지 않습니다.");
        }

        if (!reply.getWriterId().equals(requesterId)) {
            throw new UnauthorizedAccessException("해당 대댓글에 대한 삭제 권한이 없습니다.");
        }

        // 첨부파일 삭제
        List<Attachment> attachments = attachmentRepository.findByTargetIdAndTargetType(replyId, AttachmentTargetType.REPLY);
        attachments.forEach(fileStorageService::deleteFile);
        attachmentRepository.deleteAll(attachments);

        replyRepository.delete(reply);
    }

    /**
     * 첨부파일 다운로드
     */
    public Resource loadAttachmentFile(Long replyId, Long fileId) {
        Attachment file = attachmentRepository.findByIdAndTargetIdAndTargetType(fileId, replyId, AttachmentTargetType.REPLY)
                .orElseThrow(() -> new FileUploadException("해당 첨부파일을 찾을 수 없습니다."));
        return fileStorageService.loadFileAsResource(file);
    }

    /**
     * 응답 DTO 생성
     */
    private ReplyResponse buildReplyResponse(Reply reply) {
        return ReplyResponse.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .writerId(reply.getWriterId())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .liked(false)
                .likeCount(0)
                .chatEntryUrl("/api/chat/start?userId=" + reply.getWriterId())
                .build();
    }
}
