package com.smartcampus.back.community.like.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.like.entity.Like;
import com.smartcampus.back.community.like.repository.LikeRepository;
import com.smartcampus.back.community.post.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    /**
     * 게시글 좋아요 토글 처리
     *
     * @param user 사용자
     * @param post 게시글
     * @return 좋아요가 추가되었으면 true, 제거되었으면 false
     */
    @Transactional
    public boolean togglePostLike(User user, Post post) {
        return toggleLike(user, post, null, null);
    }

    /**
     * 댓글 좋아요 토글 처리
     *
     * @param user 사용자
     * @param comment 댓글
     * @return 좋아요가 추가되었으면 true, 제거되었으면 false
     */
    @Transactional
    public boolean toggleCommentLike(User user, Comment comment) {
        return toggleLike(user, null, comment, null);
    }

    /**
     * 대댓글 좋아요 토글 처리
     *
     * @param user 사용자
     * @param reply 대댓글 (Comment)
     * @return 좋아요가 추가되었으면 true, 제거되었으면 false
     */
    @Transactional
    public boolean toggleReplyLike(User user, Comment reply) {
        return toggleLike(user, null, null, reply);
    }

    /**
     * 좋아요 토글 로직 (게시글, 댓글, 대댓글에 대해 하나만 설정 가능)
     *
     * @param user 사용자
     * @param post 게시글 (nullable)
     * @param comment 댓글 (nullable)
     * @param reply 대댓글 (nullable)
     * @return 좋아요가 추가되었으면 true, 제거되었으면 false
     */
    private boolean toggleLike(User user, Post post, Comment comment, Comment reply) {
        Like existingLike = likeRepository.findByUserAndPostAndCommentAndReply(user, post, comment, reply)
                .orElse(null);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return false;
        }

        Like newLike = Like.builder()
                .user(user)
                .post(post)
                .comment(comment)
                .reply(reply)
                .build();

        likeRepository.save(newLike);
        return true;
    }
}
