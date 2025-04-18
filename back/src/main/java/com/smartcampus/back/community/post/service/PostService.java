package com.smartcampus.back.community.post.service;

import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.community.post.dto.request.PostCreateRequest;
import com.smartcampus.back.community.post.dto.request.PostUpdateRequest;
import com.smartcampus.back.community.post.dto.response.PostSimpleResponse;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.repository.PostRepository;
import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 작성, 수정, 삭제 등의 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 생성
     *
     * @param request 게시글 작성 요청 DTO
     * @param writer  작성자 (현재 로그인한 사용자)
     * @return 생성된 게시글 ID 및 메시지를 포함한 응답 DTO
     */
    public PostSimpleResponse createPost(PostCreateRequest request, User writer) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .writer(writer)
                .build();

        Post saved = postRepository.save(post);
        return new PostSimpleResponse(saved.getId(), "게시글이 성공적으로 등록되었습니다.");
    }

    /**
     * 게시글 수정
     *
     * @param postId  수정할 게시글 ID
     * @param request 수정 요청 DTO
     * @param user    요청 사용자
     * @return 수정 완료 메시지를 포함한 응답 DTO
     */
    public PostSimpleResponse updatePost(Long postId, PostUpdateRequest request, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if (!post.getWriter().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        post.update(request.getTitle(), request.getContent(), request.getCategory());

        return new PostSimpleResponse(post.getId(), "게시글이 성공적으로 수정되었습니다.");
    }

    /**
     * 게시글 삭제
     *
     * @param postId 게시글 ID
     * @param user   요청 사용자
     * @return 삭제 완료 메시지를 포함한 응답 DTO
     */
    public PostSimpleResponse deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (!post.getWriter().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        postRepository.delete(post);
        return new PostSimpleResponse(postId, "게시글이 삭제되었습니다.");
    }
}
