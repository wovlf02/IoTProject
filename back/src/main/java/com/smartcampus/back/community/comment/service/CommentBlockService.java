package com.smartcampus.back.community.comment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.CommentBlock;
import com.smartcampus.back.community.block.repository.CommentBlockRepository;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 및 대댓글 차단 처리 서비스
 * 사용자가 댓글을 피드에서 숨기도록 설정하거나 해제할 수 있습니다.
 */
@Service
@RequiredArgsConstructor
public class CommentBlockService {

    private final CommentBlockRepository commentBlockRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 또는 대댓글 차단
     *
     * @param user     차단을 요청한 사용자
     * @param commentId 차단할 댓글 ID
     */
    @Transactional
    public void blockComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        boolean alreadyBlocked = commentBlockRepository.existsByUserAndComment(user, comment);
        if (alreadyBlocked) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "이미 차단한 댓글입니다.");
        }

        CommentBlock block = CommentBlock.builder()
                .user(user)
                .comment(comment)
                .build();

        commentBlockRepository.save(block);
    }

    /**
     * 댓글 또는 대댓글 차단 해제
     *
     * @param user       사용자
     * @param commentId  해제할 댓글 ID
     */
    @Transactional
    public void unblockComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        CommentBlock block = commentBlockRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "차단 내역이 없습니다."));

        commentBlockRepository.delete(block);
    }

    /**
     * 해당 댓글이 차단된 상태인지 확인
     */
    @Transactional(readOnly = true)
    public boolean isBlocked(User user, Comment comment) {
        return commentBlockRepository.existsByUserAndComment(user, comment);
    }
}
