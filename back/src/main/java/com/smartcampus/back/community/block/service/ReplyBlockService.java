package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.ReplyBlock;
import com.smartcampus.back.community.block.repository.ReplyBlockRepository;
import com.smartcampus.back.community.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 대댓글 차단 관련 비즈니스 로직 처리 서비스
 */
@Service
@RequiredArgsConstructor
public class ReplyBlockService {

    private final ReplyBlockRepository replyBlockRepository;

    /**
     * 대댓글 차단
     *
     * @param user  차단 요청한 사용자
     * @param reply 차단 대상 대댓글
     */
    @Transactional
    public void block(User user, Comment reply) {
        boolean alreadyBlocked = replyBlockRepository.findByUserAndReply(user, reply).isPresent();
        if (!alreadyBlocked) {
            ReplyBlock block = ReplyBlock.builder()
                    .user(user)
                    .reply(reply)
                    .build();
            replyBlockRepository.save(block);
        }
    }

    /**
     * 대댓글 차단 해제
     *
     * @param user  요청 사용자
     * @param reply 해제 대상 대댓글
     */
    @Transactional
    public void unblock(User user, Comment reply) {
        replyBlockRepository.findByUserAndReply(user, reply)
                .ifPresent(replyBlockRepository::delete);
    }
}
