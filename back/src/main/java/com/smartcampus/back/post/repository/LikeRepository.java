package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Like;
import com.smartcampus.back.post.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    // --- 게시글 좋아요 ---
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
    void deleteByPostId(Long postId);

    // --- 댓글 좋아요 ---
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
    long countByCommentId(Long commentId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
    void deleteByCommentId(Long commentId);

    // --- 대댓글 좋아요 ---
    boolean existsByUserIdAndReplyId(Long userId, Long replyId);
    long countByReplyId(Long replyId);
    void deleteByUserIdAndReplyId(Long userId, Long replyId);
    void deleteByReplyId(Long replyId);
}
