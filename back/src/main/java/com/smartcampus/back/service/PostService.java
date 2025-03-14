package com.smartcampus.back.service;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.PostAttachmentRepository;
import com.smartcampus.back.repository.PostLikeRepository;
import com.smartcampus.back.repository.PostRepository;
import com.smartcampus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시판 관리 서비스
 *
 * 게시글 작성, 수정, 삭제
 * 게시글 목록 조회, 상세 조회, 검색
 * 게시글 추천 기능
 * 게시글 신고 기능
 * 게시글 첨부파일 업로드
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostAttachmentRepository postAttachmentRepository;
    private final UserRepository userRepository;

    // ====================== 게시글 작성 ========================

    /**
     * 게시글 작성
     * 사용자가 제목, 내용 및 선택적으로 파일을 업로드하여 게시글을 작성할 수 있다.
     * @param request 게시글 작성 요청 DTO (제목, 내용 포함)
     * @param userId 작성자 ID
     * @param files 첨부파일 (선택 사항)
     * @return 작성된 게시글 정보
     */
    @Transactional
    public PostResponse createPost(PostRequest request, Long userId, List<MultipartFile> files) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 게시글 저장
        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        postRepository.save(post);

        // 첨부파일 저장 (선택 사항)
        if (files != null) {
            savePostAttachments(post, files);
        }

        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }

    // ====================== 게시글 수정 ========================

    /**
     * 게시글 수정
     * 사용자는 자신이 작성한 게시글을 수정할 수 있다.
     * @param postId 수정할 게시글 ID
     * @param request 게시글 수정 요청 DTO
     * @param userId
     * @param files
     * @return
     */
    @Transactional
    public PostResponse updatePost(Long postId, PostRequest request, Long userId, List<MultipartFile> files) {
        Post post = getPostById(postId);

        // 게시글 작성자 확인
        if(!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
        }

        // 게시글 내용 수정
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);

        // 첨부파일 수정 (기존 파일 삭제 후 새로운 파일 저장)
        if(files != null) {
            postAttachmentRepository.deleteByPost(post);
            savePostAttachments(post, files);
        }

        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
