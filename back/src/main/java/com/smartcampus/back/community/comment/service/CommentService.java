package com.smartcampus.back.community.comment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.dto.request.CommentCreateRequest;
import com.smartcampus.back.community.comment.dto.request.CommentUpdateRequest;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.repository.PostRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 댓글 및 대댓글의 생성, 수정, 삭제 등의 기능을 제공하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 또는 대댓글을 작성합니다.
     *
     * @param user 현재 로그인한 사용자
     * @param postId 대상 게시글 ID
     * @param request 댓글 생성 요청 DTO
     * @return 생성된 댓글의 ID
     */
    @Transactional
    public Long createComment(User user, Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글이 존재하지 않습니다."));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "상위 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .parent(parentComment)
                .content(request.getContent())
                .isDeleted(false)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글을 수정합니다.
     *
     * @param user 현재 로그인한 사용자
     * @param commentId 수정 대상 댓글 ID
     * @param request 수정 요청 DTO
     */
    @Transactional
    public void updateComment(User user, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION, "댓글을 수정할 권한이 없습니다.");
        }

        comment.updateContent(request.getContent());
    }

    /**
     * 댓글을 삭제합니다. (Soft Delete)
     *
     * @param user 현재 로그인한 사용자
     * @param commentId 삭제 대상 댓글 ID
     */
    @Transactional
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION, "댓글을 삭제할 권한이 없습니다.");
        }

        comment.markAsDeleted();
    }
}
