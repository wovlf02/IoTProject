package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.like.LikeResponse;
import com.smartcampus.back.post.entity.*;
import com.smartcampus.back.post.exception.*;
import com.smartcampus.back.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글, 댓글, 대댓글에 대한 좋아요 기능을 처리하는 서비스 클래스입니다.
 * 각 대상에 대해 좋아요 토글, 개수 조회, 좋아요 여부 확인 기능을 제공합니다.
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
     * 게시글 좋아요 토글 처리
     *
     * @param postId 게시글 ID
     * @param userId 사용자 ID
     * @return LikeResponse (상태 메시지, 총 개수, 좋아요 여부)
     */
    public LikeResponse togglePostLike(Long postId, Long userId) {
        LikeId likeId = new LikeId(userId, postId, null, null);

        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
            return buildLikeResponse(false, likeRepository.countByPostId(postId), "게시글 좋아요가 취소되었습니다.");
        } else {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

            Like like = Like.builder()
                    .id(likeId)
                    .post(post)
                    .build();
            likeRepository.save(like);
            return buildLikeResponse(true, likeRepository.countByPostId(postId), "게시글 좋아요가 추가되었습니다.");
        }
    }

    /**
     * 댓글 좋아요 토글 처리
     *
     * @param commentId 댓글 ID
     * @param userId 사용자 ID
     * @return LikeResponse
     */
    public LikeResponse toggleCommentLike(Long commentId, Long userId) {
        LikeId likeId = new LikeId(userId, null, commentId, null);

        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
            return buildLikeResponse(false, likeRepository.countByCommentId(commentId), "댓글 좋아요가 취소되었습니다.");
        } else {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

            Like like = Like.builder()
                    .id(likeId)
                    .comment(comment)
                    .build();
            likeRepository.save(like);
            return buildLikeResponse(true, likeRepository.countByCommentId(commentId), "댓글 좋아요가 추가되었습니다.");
        }
    }

    /**
     * 대댓글 좋아요 토글 처리
     *
     * @param replyId 대댓글 ID
     * @param userId 사용자 ID
     * @return LikeResponse
     */
    public LikeResponse toggleReplyLike(Long replyId, Long userId) {
        LikeId likeId = new LikeId(userId, null, null, replyId);

        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
            return buildLikeResponse(false, likeRepository.countByReplyId(replyId), "대댓글 좋아요가 취소되었습니다.");
        } else {
            Reply reply = replyRepository.findById(replyId)
                    .orElseThrow(() -> new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다."));

            Like like = Like.builder()
                    .id(likeId)
                    .reply(reply)
                    .build();
            likeRepository.save(like);
            return buildLikeResponse(true, likeRepository.countByReplyId(replyId), "대댓글 좋아요가 추가되었습니다.");
        }
    }

    /**
     * 게시글 좋아요 여부 확인
     */
    public boolean isPostLiked(Long postId, Long userId) {
        return likeRepository.existsById(new LikeId(userId, postId, null, null));
    }

    /**
     * 댓글 좋아요 여부 확인
     */
    public boolean isCommentLiked(Long commentId, Long userId) {
        return likeRepository.existsById(new LikeId(userId, null, commentId, null));
    }

    /**
     * 대댓글 좋아요 여부 확인
     */
    public boolean isReplyLiked(Long replyId, Long userId) {
        return likeRepository.existsById(new LikeId(userId, null, null, replyId));
    }

    /**
     * 게시글 좋아요 수 조회
     */
    public long getLikeCountByPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    /**
     * 댓글 좋아요 수 조회
     */
    public long getLikeCountByComment(Long commentId) {
        return likeRepository.countByCommentId(commentId);
    }

    /**
     * 대댓글 좋아요 수 조회
     */
    public long getLikeCountByReply(Long replyId) {
        return likeRepository.countByReplyId(replyId);
    }

    /**
     * LikeResponse 공통 생성 메서드
     */
    private LikeResponse buildLikeResponse(boolean liked, long count, String message) {
        return LikeResponse.builder()
                .liked(liked)
                .totalLikes((int) count)
                .message(message)
                .build();
    }
}
