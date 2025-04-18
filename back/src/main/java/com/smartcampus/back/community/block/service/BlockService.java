package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.community.block.service.CommentBlockService;
import com.smartcampus.back.community.block.service.PostBlockService;
import com.smartcampus.back.community.block.service.ReplyBlockService;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.community.comment.repository.ReplyRepository;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글, 댓글, 대댓글 차단을 분기 처리하는 통합 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BlockService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final PostBlockService postBlockService;
    private final CommentBlockService commentBlockService;
    private final ReplyBlockService replyBlockService;

    /**
     * 게시글 차단 요청
     */
    public void blockPost(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postBlockService.block(user, post);
    }

    /**
     * 게시글 차단 해제
     */
    public void unblockPost(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postBlockService.unblock(user, post);
    }

    /**
     * 댓글 차단 요청
     */
    public void blockComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentBlockService.block(user, comment);
    }

    /**
     * 댓글 차단 해제
     */
    public void unblockComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentBlockService.unblock(user, comment);
    }

    /**
     * 대댓글 차단 요청
     */
    public void blockReply(User user, Long replyId) {
        Comment reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));
        replyBlockService.block(user, reply);
    }

    /**
     * 대댓글 차단 해제
     */
    public void unblockReply(User user, Long replyId) {
        Comment reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPLY_NOT_FOUND));
        replyBlockService.unblock(user, reply);
    }
}
