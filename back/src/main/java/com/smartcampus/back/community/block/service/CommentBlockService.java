package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.CommentBlock;
import com.smartcampus.back.community.block.repository.CommentBlockRepository;
import com.smartcampus.back.community.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 차단 관련 비즈니스 로직 처리 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentBlockService {

    private final CommentBlockRepository commentBlockRepository;

    /**
     * 댓글 차단
     *
     * @param user    차단 요청을 한 사용자
     * @param comment 차단 대상 댓글
     */
    @Transactional
    public void block(User user, Comment comment) {
        boolean alreadyBlocked = commentBlockRepository.findByUserAndComment(user, comment).isPresent();
        if (!alreadyBlocked) {
            CommentBlock block = CommentBlock.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            commentBlockRepository.save(block);
        }
    }

    /**
     * 댓글 차단 해제
     *
     * @param user    요청 사용자
     * @param comment 해제 대상 댓글
     */
    @Transactional
    public void unblock(User user, Comment comment) {
        commentBlockRepository.findByUserAndComment(user, comment)
                .ifPresent(commentBlockRepository::delete);
    }
}
