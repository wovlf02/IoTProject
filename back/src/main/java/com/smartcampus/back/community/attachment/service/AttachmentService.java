package com.smartcampus.back.community.attachment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.community.attachment.dto.response.AttachmentListResponse;
import com.smartcampus.back.community.attachment.dto.response.AttachmentResponse;
import com.smartcampus.back.community.attachment.dto.response.AttachmentUploadResponse;
import com.smartcampus.back.community.attachment.entity.Attachment;
import com.smartcampus.back.community.attachment.repository.AttachmentRepository;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.smartcampus.back.common.util.FileUtil.saveFile;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;

    /**
     * 게시글 첨부파일 업로드
     */
    @Transactional
    public AttachmentUploadResponse uploadPostFiles(Long postId, List<MultipartFile> files) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 작성자 정보는 게시글에서 조회
        User uploader = post.getUser();

        List<Long> uploadedIds = files.stream()
                .map(file -> saveAttachment(file, uploader, post, null, null))
                .map(attachmentRepository::save)
                .map(Attachment::getId)
                .toList();

        return AttachmentUploadResponse.of(uploadedIds);
    }

    /**
     * 댓글 첨부파일 업로드
     */
    @Transactional
    public AttachmentUploadResponse uploadCommentFiles(Long commentId, List<MultipartFile> files) throws IOException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        User uploader = comment.getUser();

        List<Long> uploadedIds = files.stream()
                .map(file -> saveAttachment(file, uploader, null, comment, null))
                .map(attachmentRepository::save)
                .map(Attachment::getId)
                .toList();

        return AttachmentUploadResponse.of(uploadedIds);
    }

    /**
     * 대댓글 첨부파일 업로드
     */
    @Transactional
    public AttachmentUploadResponse uploadReplyFiles(Long replyId, List<MultipartFile> files) throws IOException {
        Comment reply = commentRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));

        User uploader = reply.getUser();

        List<Long> uploadedIds = files.stream()
                .map(file -> saveAttachment(file, uploader, null, null, reply))
                .map(attachmentRepository::save)
                .map(Attachment::getId)
                .toList();

        return AttachmentUploadResponse.of(uploadedIds);
    }

    /**
     * 게시글 첨부파일 목록 조회
     */
    public AttachmentListResponse getPostAttachments(Long postId) {
        List<AttachmentResponse> list = attachmentRepository.findAllByPostId(postId).stream()
                .map(AttachmentResponse::from)
                .toList();
        return AttachmentListResponse.of(list);
    }

    /**
     * 댓글 첨부파일 목록 조회
     */
    public AttachmentListResponse getCommentAttachments(Long commentId) {
        List<AttachmentResponse> list = attachmentRepository.findAllByCommentId(commentId).stream()
                .map(AttachmentResponse::from)
                .toList();
        return AttachmentListResponse.of(list);
    }

    /**
     * 대댓글 첨부파일 목록 조회
     */
    public AttachmentListResponse getReplyAttachments(Long replyId) {
        List<AttachmentResponse> list = attachmentRepository.findAllByReplyId(replyId).stream()
                .map(AttachmentResponse::from)
                .toList();
        return AttachmentListResponse.of(list);
    }

    /**
     * 첨부파일 삭제
     */
    @Transactional
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        fileStorageService.deletePhysicalFile(attachment.getFilePath());
        attachmentRepository.delete(attachment);
    }

    /**
     * 내부 공용 저장 로직
     */
    private Attachment saveAttachment(MultipartFile file, User uploader, Post post, Comment comment, Comment reply) {
        try {
            String savedName = fileStorageService.save(file, post, comment, reply);
            String path = fileStorageService.buildFullPath(post, comment, reply, savedName);

            return Attachment.builder()
                    .filePath(path)
                    .savedName(savedName)
                    .originalName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .uploader(uploader)
                    .post(post)
                    .comment(comment)
                    .reply(reply)
                    .uploadedAt(LocalDateTime.now())
                    .build();

        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED, "파일 저장 중 오류 발생");
        }
    }
}
