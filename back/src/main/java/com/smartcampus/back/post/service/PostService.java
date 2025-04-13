package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.attachment.FileDownloadResponse;
import com.smartcampus.back.post.dto.post.*;
import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.enums.AttachmentTargetType;
import com.smartcampus.back.post.exception.FileUploadException;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.exception.UnauthorizedAccessException;
import com.smartcampus.back.post.repository.AttachmentRepository;
import com.smartcampus.back.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 생성, 수정, 삭제, 조회, 첨부파일 관리, 검색 등을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    /**
     * 게시글 생성
     */
    public PostCreateResponse createPost(PostCreateRequest request, List<MultipartFile> files) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writerId(request.getWriterId())
                .isPublic(request.isPublic())
                .viewCount(0)
                .build();

        Post savedPost = postRepository.save(post);

        if (files != null && !files.isEmpty()) {
            List<Attachment> attachments = files.stream()
                    .map(file -> fileStorageService.storeFile(file, savedPost.getId(), AttachmentTargetType.POST))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(attachments);
        }

        return PostCreateResponse.builder()
                .postId(savedPost.getId())
                .message("게시글이 성공적으로 등록되었습니다.")
                .build();
    }

    /**
     * 게시글 수정
     */
    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest request, List<MultipartFile> newFiles) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        if (!post.getWriterId().equals(request.getWriterId())) {
            throw new UnauthorizedAccessException("해당 게시글에 대한 수정 권한이 없습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPublic(request.isPublic());
        post.setUpdatedAt(LocalDateTime.now());

        if (request.getDeleteFiles() != null && !request.getDeleteFiles().isEmpty()) {
            request.getDeleteFiles().forEach(fileId -> {
                Attachment file = attachmentRepository.findByIdAndTargetIdAndTargetType(
                        fileId, postId, AttachmentTargetType.POST
                ).orElseThrow(() -> new FileUploadException("삭제할 파일이 존재하지 않습니다."));
                fileStorageService.deleteFile(file);
                attachmentRepository.delete(file);
            });
        }

        if (newFiles != null && !newFiles.isEmpty()) {
            List<Attachment> added = newFiles.stream()
                    .map(f -> fileStorageService.storeFile(f, postId, AttachmentTargetType.POST))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(added);
        }

        return PostUpdateResponse.builder()
                .postId(post.getId())
                .message("게시글이 성공적으로 수정되었습니다.")
                .build();
    }

    /**
     * 게시글 삭제
     */
    public PostDeleteResponse deletePost(Long postId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("삭제할 게시글이 존재하지 않습니다."));

        if (!post.getWriterId().equals(requesterId)) {
            throw new UnauthorizedAccessException("해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        List<Attachment> attachments = attachmentRepository.findByTargetIdAndTargetType(postId, AttachmentTargetType.POST);
        attachments.forEach(fileStorageService::deleteFile);
        attachmentRepository.deleteAll(attachments);
        postRepository.delete(post);

        return PostDeleteResponse.builder()
                .postId(postId)
                .message("게시글이 성공적으로 삭제되었습니다.")
                .build();
    }

    /**
     * 게시글 상세 조회 + 조회수 증가
     */
    @Transactional
    public PostDetailResponse getPostDetailAndIncreaseView(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        post.setViewCount(post.getViewCount() + 1); // 조회수 증가
        postRepository.save(post); // 변경 감지 or save 생략 가능 (dirty checking)

        List<FileDownloadResponse> files = attachmentRepository.findByTargetIdAndTargetType(postId, AttachmentTargetType.POST).stream()
                .map(fileStorageService::mapToDownloadResponse)
                .collect(Collectors.toList());

        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriterId())
                .isPublic(post.isPublic())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .attachments(files)
                .build();
    }

    /**
     * 게시글 목록 조회 (페이징)
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> result = postRepository.findAll(pageable);

        return result.stream()
                .map(post -> PostResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .writerId(post.getWriterId())
                        .viewCount(post.getViewCount())
                        .createdAt(post.getCreatedAt())
                        .likeCount(post.getLikes().size())
                        .commentCount(post.getComments().size())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 게시글 검색 (제목 또는 내용)
     */
    @Transactional(readOnly = true)
    public List<PostResponse> searchPosts(String keyword) {
        Pageable pageable = PageRequest.of(0, 50);
        Page<Post> posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);

        return posts.stream()
                .map(post -> PostResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .writerId(post.getWriterId())
                        .viewCount(post.getViewCount())
                        .createdAt(post.getCreatedAt())
                        .likeCount(post.getLikes().size())
                        .commentCount(post.getComments().size())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 게시글 첨부파일 다운로드용 파일 로드
     */
    public Resource loadFileAsResource(Long postId, Long fileId) {
        Attachment attachment = attachmentRepository.findByIdAndTargetIdAndTargetType(
                fileId, postId, AttachmentTargetType.POST
        ).orElseThrow(() -> new FileUploadException("해당 첨부파일을 찾을 수 없습니다."));
        return fileStorageService.loadFileAsResource(attachment);
    }

    /**
     * 게시글 첨부파일 삭제 (작성자 검증 포함)
     */
    public void deleteAttachment(Long postId, Long fileId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));

        if (!post.getWriterId().equals(requesterId)) {
            throw new UnauthorizedAccessException("해당 첨부파일을 삭제할 권한이 없습니다.");
        }

        Attachment attachment = attachmentRepository.findByIdAndTargetIdAndTargetType(
                fileId, postId, AttachmentTargetType.POST
        ).orElseThrow(() -> new FileUploadException("첨부파일이 존재하지 않습니다."));

        fileStorageService.deleteFile(attachment);
        attachmentRepository.delete(attachment);
    }
}
