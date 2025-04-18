package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글, 댓글, 대댓글 차단을 분기 처리하는 통합 서비스
 */
@Service
@RequiredArgsConstructor
public class BlockService {

    private final PostBlockService postBlockService;
    private final CommentBlockService commentBlockService;
    private final ReplyBlockService replyBlockService;

    /**
     * 게시글 차단 요청
     *
     * @param user 차단을 요청한 사용자
     * @param post 대상 게시글
     */
    public void blockPost(User user, Post post) {
        postBlockService.block(user, post);
    }

    /**
     * 댓글 차단 요청
     *
     * @param user 차단을 요청한 사용자
     * @param comment 대상 댓글
     */
    public void blockComment(User user, Comment comment) {
        commentBlockService.block(user, comment);
    }

    /**
     * 대댓글 차단 요청
     *
     * @param user 차단을 요청한 사용자
     * @param reply 대상 대댓글
     */
    public void blockReply(User user, Comment reply) {
        replyBlockService.block(user, reply);
    }

    /**
     * 게시글 차단 해제
     */
    public void unblockPost(User user, Post post) {
        postBlockService.unblock(user, post);
    }

    /**
     * 댓글 차단 해제
     */
    public void unblockComment(User user, Comment comment) {
        commentBlockService.unblock(user, comment);
    }

    /**
     * 대댓글 차단 해제
     */
    public void unblockReply(User user, Comment reply) {
        replyBlockService.unblock(user, reply);
    }
}
