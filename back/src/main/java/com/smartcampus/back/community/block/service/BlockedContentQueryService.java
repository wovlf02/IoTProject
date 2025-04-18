package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.CommentBlock;
import com.smartcampus.back.community.block.entity.PostBlock;
import com.smartcampus.back.community.block.entity.ReplyBlock;
import com.smartcampus.back.community.block.repository.CommentBlockRepository;
import com.smartcampus.back.community.block.repository.PostBlockRepository;
import com.smartcampus.back.community.block.repository.ReplyBlockRepository;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자가 차단한 게시글, 댓글, 대댓글 목록을 조회하는 서비스
 */
@Service
@RequiredArgsConstructor
public class BlockedContentQueryService {

    private final PostBlockRepository postBlockRepository;
    private final CommentBlockRepository commentBlockRepository;
    private final ReplyBlockRepository replyBlockRepository;

    /**
     * 사용자가 차단한 게시글 ID 목록 조회
     */
    public List<Long> getBlockedPostIds(User user) {
        return postBlockRepository.findAllByUser(user).stream()
                .map(PostBlock::getPost)
                .map(Post::getId)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 차단한 댓글 ID 목록 조회
     */
    public List<Long> getBlockedCommentIds(User user) {
        return commentBlockRepository.findAllByUser(user).stream()
                .map(CommentBlock::getComment)
                .map(Comment::getId)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 차단한 대댓글 ID 목록 조회
     */
    public List<Long> getBlockedReplyIds(User user) {
        return replyBlockRepository.findAllByUser(user).stream()
                .map(ReplyBlock::getReply)
                .map(Comment::getId)
                .collect(Collectors.toList());
    }
}
