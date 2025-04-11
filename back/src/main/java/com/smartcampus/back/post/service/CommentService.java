package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.comment.CommentCreateRequest;
import com.smartcampus.back.post.dto.comment.CommentResponse;
import com.smartcampus.back.post.dto.comment.CommentUpdateRequest;
import com.smartcampus.back.post.entity.Comment;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.exception.CommentNotFoundException;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.repository.CommentRepository;
import com.smartcampus.back.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 댓글(Comment) 관련 서비스 계층
 * 댓글 작성, 수정, 삭제 등의 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 게시글에 새로운 댓글을 작성
     *
     * @param postId 댓글이 달릴 게시글의 ID
     * @param request 댓글 생성 요청 DTO (내용, 작성자 ID 등)
     * @return 생성된 댓글 응답 DTO
     */
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .post(post)
                .writerId(request.getWriterId())
                .content(request.getContent())
                .build();

        Comment saved = commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(saved.getId())
                .content(saved.getContent())
                .writerId(saved.getWriterId())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getCreatedAt()) // 최초 생성 시점 -> 수정 시점
                .build();
    }

    /**
     * 댓글 내용 수정
     *
     * @param postId 대상 게시글 ID
     * @param commentId 수정할 댓글 ID
     * @param request 댓글 수정 요청 DTO
     * @return 수정된 댓글 응답 DTO
     */
    public CommentResponse updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId);

        if(comment == null) {
            throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
        }

        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updated = commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(updated.getId())
                .content(updated.getContent())
                .writerId(updated.getWriterId())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }

    /**
     * 댓글 삭제
     *
     * @param postId 대상 게시글 ID
     * @param commentId 삭제할 댓글 ID
     */
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId);

        if(comment == null) {
            throw new CommentNotFoundException("삭제하려는 댓글이 존재하지 않습니다.");
        }

        commentRepository.delete(comment);
    }
}
