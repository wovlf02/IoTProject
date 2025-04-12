package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.attachment.FileDownloadResponse;
import com.smartcampus.back.post.dto.post.*;
import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.exception.FileUploadException;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.exception.UnauthorizedAccessException;
import com.smartcampus.back.post.repository.AttachmentRepository;
import com.smartcampus.back.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 생성, 수정, 삭제, 조회, 검색 및 첨부파일 연동 포함
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
                    .map(file -> fileStorageService.storeFile(file, savedPost))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(attachments);
        }

        return PostCreateResponse.builder()
                .postId(savedPost.getId())
                .message("게시글이 성공적으로 등록되었습니다.")
                .build();
    }

    /**
     * 게시글 수정 (작성자 검증 포함)
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

        // 첨부파일 삭제
        if (request.getDeleteFiles() != null && !request.getDeleteFiles().isEmpty()) {
            request.getDeleteFiles().forEach(fileId -> {
                Attachment file = attachmentRepository.findById(fileId)
                        .orElseThrow(() -> new FileUploadException("삭제할 파일이 존재하지 않습니다."));
                fileStorageService.deleteFile(file);
                attachmentRepository.delete(file);
            });
        }

        // 새 첨부파일 추가
        if (newFiles != null && !newFiles.isEmpty()) {
            List<Attachment> added = newFiles.stream()
                    .map(f -> fileStorageService.storeFile(f, post))
                    .collect(Collectors.toList());
            attachmentRepository.saveAll(added);
        }

        return PostUpdateResponse.builder()
                .postId(post.getId())
                .message("게시글이 성공적으로 수정되었습니다.")
                .build();
    }

    /**
     * 게시글 삭제 (작성자 검증 포함)
     */
    public PostDeleteResponse deletePost(Long postId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("삭제할 게시글이 존재하지 않습니다."));

        if (!post.getWriterId().equals(requesterId)) {
            throw new UnauthorizedAccessException("해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        List<Attachment> attachments = attachmentRepository.findByPostId(postId);
        attachments.forEach(fileStorageService::deleteFile);
        attachmentRepository.deleteAll(attachments);
        postRepository.delete(post);

        return PostDeleteResponse.builder()
                .postId(postId)
                .message("게시글이 성공적으로 삭제되었습니다.")
                .build();
    }

    /**
     * 게시글 상세 조회 (첨부파일 포함)
     */
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        List<FileDownloadResponse> files = attachmentRepository.findByPostId(postId).stream()
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
        Pageable pageable = PageRequest.of(page, size);
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
     * 게시글 검색 (제목 or 내용)
     */
    @Transactional(readOnly = true)
    public List<PostResponse> searchPosts(String keyword) {
        Pageable pageable = PageRequest.of(0, 50); // 기본값으로 50개까지 조회
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
     * 첨부파일 단건 다운로드
     */
    public Resource loadFileAsResource(Long postId, Long fileId) {
        Attachment attachment = attachmentRepository.findByIdAndPostId(fileId, postId);
        return fileStorageService.loadFileAsResource(attachment);
    }

    /**
     * 첨부파일 삭제 (작성자 검증)
     */
    public void deleteAttachment(Long postId, Long fileId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));

        if (!post.getWriterId().equals(requesterId)) {
            throw new UnauthorizedAccessException("해당 첨부파일을 삭제할 권한이 없습니다.");
        }

        Attachment attachment = attachmentRepository.findByIdAndPostId(fileId, postId);
        fileStorageService.deleteFile(attachment);
        attachmentRepository.delete(attachment);
    }
}
