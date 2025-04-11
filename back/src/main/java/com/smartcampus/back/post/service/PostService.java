package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.post.PostCreateRequest;
import com.smartcampus.back.post.dto.post.PostCreateResponse;
import com.smartcampus.back.post.dto.post.PostUpdateRequest;
import com.smartcampus.back.post.dto.post.PostUpdateResponse;
import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.repository.AttachmentRepository;
import com.smartcampus.back.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글(Post) 관련 서비스 클래스
 * 게시글 생성, 수정, 삭제, 상세 조회, 목록 조회, 검색 담당
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    public PostCreateResponse createPost(PostCreateRequest request, List<MultipartFile> files) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writerId(request.getWriterId())
                .isPublic(request.isPublic())
                .viewCount(0)
                .build();

        Post savedPost = postRepository.save(post);

        if (files != null) {
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

    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest request, List<MultipartFile> newFiles) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPublic(request.isPublic());
        post.setUpdatedAt(LocalDateTime.now());

        if(request.getDeleteFileIds() != null) {
            request.getDeleteFiles().forEach(fileId -> {
                Attachment file = attachmentRepository.findById(fileId)
                        .orElseThrow(() -> new RuntimeException("삭제할 파일이 존재하지 않습니다."));
                fileStorageService.deleteFile(file);
                attachmentRepository.delete(file);
            });
        }

        if(newFiles != null) {
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
}
