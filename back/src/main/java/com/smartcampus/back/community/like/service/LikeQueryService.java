package com.smartcampus.back.community.like.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.like.repository.LikeRepository;
import com.smartcampus.back.community.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeQueryService {

    private final LikeRepository likeRepository;

    /**
     * 게시글에 대한 좋아요 여부 확인
     *
     * @param user 현재 로그인한 사용자
     * @param post 대상 게시글
     * @return 사용자가 해당 게시글에 좋아요를 눌렀다면 true, 아니면 false
     */
    @Transactional(readOnly = true)
    public boolean hasLikedPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post).isPresent();
    }

    /**
     * 댓글에 대한 좋아요 여부 확인
     *
     * @param user 현재 로그인한 사용자
     * @param comment 대상 댓글
     * @return 사용자가 해당 댓글에 좋아요를 눌렀다면 true, 아니면 false
     */
    @Transactional(readOnly = true)
    public boolean hasLikedComment(User user, Comment comment) {
        return likeRepository.findByUserAndComment(user, comment).isPresent();
    }

    /**
     * 대댓글에 대한 좋아요 여부 확인
     *
     * @param user 현재 로그인한 사용자
     * @param reply 대상 대댓글 (Comment로 간주)
     * @return 사용자가 해당 대댓글에 좋아요를 눌렀다면 true, 아니면 false
     */
    @Transactional(readOnly = true)
    public boolean hasLikedReply(User user, Comment reply) {
        return likeRepository.findByUserAndReply(user, reply).isPresent();
    }

    /**
     * 게시글에 대한 좋아요 수 반환
     *
     * @param post 게시글
     * @return 좋아요 수
     */
    @Transactional(readOnly = true)
    public long countPostLikes(Post post) {
        return likeRepository.countByPost(post);
    }

    /**
     * 댓글에 대한 좋아요 수 반환
     *
     * @param comment 댓글
     * @return 좋아요 수
     */
    @Transactional(readOnly = true)
    public long countCommentLikes(Comment comment) {
        return likeRepository.countByComment(comment);
    }

    /**
     * 대댓글에 대한 좋아요 수 반환
     *
     * @param reply 대댓글 (Comment)
     * @return 좋아요 수
     */
    @Transactional(readOnly = true)
    public long countReplyLikes(Comment reply) {
        return likeRepository.countByReply(reply);
    }
}
