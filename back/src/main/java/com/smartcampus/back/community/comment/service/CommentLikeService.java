package com.smartcampus.back.community.comment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.community.like.entity.Like;
import com.smartcampus.back.community.like.repository.LikeRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글/대댓글 좋아요 서비스
 * 사용자가 댓글 또는 대댓글에 좋아요를 추가하거나 취소하는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 또는 대댓글에 좋아요 추가
     *
     * @param user      현재 로그인 사용자
     * @param commentId 대상 댓글 또는 대댓글 ID
     */
    @Transactional
    public void like(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."));

        boolean alreadyLiked = likeRepository.existsByUserAndComment(user, comment);
        if (alreadyLiked) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "이미 좋아요를 누른 댓글입니다.");
        }

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .build();

        likeRepository.save(like);
    }

    /**
     * 댓글 또는 대댓글에 좋아요 취소
     *
     * @param user      현재 로그인 사용자
     * @param commentId 대상 댓글 또는 대댓글 ID
     */
    @Transactional
    public void unlike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."));

        Like like = likeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "좋아요를 누른 기록이 없습니다."));

        likeRepository.delete(like);
    }

    /**
     * 좋아요 여부 확인
     *
     * @param user      현재 로그인 사용자
     * @param commentId 댓글 ID
     * @return true: 좋아요 누름, false: 안 누름
     */
    @Transactional(readOnly = true)
    public boolean isLiked(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return likeRepository.existsByUserAndComment(user, comment);
    }

    /**
     * 댓글의 좋아요 수 반환
     */
    @Transactional(readOnly = true)
    public Long countLikes(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return likeRepository.countByComment(comment);
    }
}
