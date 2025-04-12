package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.like.LikeResponse;
import com.smartcampus.back.post.entity.*;
import com.smartcampus.back.post.exception.*;
import com.smartcampus.back.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글, 댓글, 대댓글에 대한 좋아요 기능을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    /**
     * 게시글 좋아요 토글
     */
    public LikeResponse togglePostLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .post(post)
                    .build();
            likeRepository.save(like);
        }

        long totalLikes = likeRepository.countByPostId(postId);

        return LikeResponse.builder()
                .liked(!alreadyLiked)
                .totalLikes((int) totalLikes)
                .message(alreadyLiked ? "게시글 좋아요가 취소되었습니다." : "게시글 좋아요가 추가되었습니다.")
                .build();
    }

    /**
     * 댓글 좋아요 토글
     */
    public LikeResponse toggleCommentLike(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

        boolean alreadyLiked = likeRepository.existsByUserIdAndCommentId(userId, commentId);

        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndCommentId(userId, commentId);
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .comment(comment)
                    .build();
            likeRepository.save(like);
        }

        long totalLikes = likeRepository.countByCommentId(commentId);

        return LikeResponse.builder()
                .liked(!alreadyLiked)
                .totalLikes((int) totalLikes)
                .message(alreadyLiked ? "댓글 좋아요가 취소되었습니다." : "댓글 좋아요가 추가되었습니다.")
                .build();
    }

    /**
     * 대댓글 좋아요 토글
     */
    public LikeResponse toggleReplyLike(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다."));

        boolean alreadyLiked = likeRepository.existsByUserIdAndReplyId(userId, replyId);

        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndReplyId(userId, replyId);
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .reply(reply)
                    .build();
            likeRepository.save(like);
        }

        long totalLikes = likeRepository.countByReplyId(replyId);

        return LikeResponse.builder()
                .liked(!alreadyLiked)
                .totalLikes((int) totalLikes)
                .message(alreadyLiked ? "대댓글 좋아요가 취소되었습니다." : "대댓글 좋아요가 추가되었습니다.")
                .build();
    }

    // 게시글 좋아요 개수
    public long getLikeCountByPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    // 댓글 좋아요 개수
    public long getLikeCountByComment(Long commentId) {
        return likeRepository.countByCommentId(commentId);
    }

    // 대댓글 좋아요 개수
    public long getLikeCountByReply(Long replyId) {
        return likeRepository.countByReplyId(replyId);
    }

    // 게시글 좋아요 여부
    public boolean isPostLiked(Long postId, Long userId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    // 댓글 좋아요 여부
    public boolean isCommentLiked(Long commentId, Long userId) {
        return likeRepository.existsByUserIdAndCommentId(userId, commentId);
    }

    // 대댓글 좋아요 여부
    public boolean isReplyLiked(Long replyId, Long userId) {
        return likeRepository.existsByUserIdAndReplyId(userId, replyId);
    }

}
