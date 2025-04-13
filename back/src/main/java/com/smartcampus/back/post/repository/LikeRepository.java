package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Like;
import com.smartcampus.back.post.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    // 게시글 좋아요 여부 확인
    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.id.userId = :userId AND l.post.id = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    // 게시글 좋아요 수
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);

    // 게시글 좋아요 삭제
    @Query("DELETE FROM Like l WHERE l.id.userId = :userId AND l.post.id = :postId")
    void deleteByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);


    // 댓글 좋아요 여부 확인
    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.id.userId = :userId AND l.comment.id = :commentId")
    boolean existsByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);

    // 댓글 좋아요 수
    @Query("SELECT COUNT(l) FROM Like l WHERE l.comment.id = :commentId")
    long countByCommentId(@Param("commentId") Long commentId);

    // 댓글 좋아요 삭제
    @Query("DELETE FROM Like l WHERE l.id.userId = :userId AND l.comment.id = :commentId")
    void deleteByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);


    // 대댓글 좋아요 여부 확인
    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.id.userId = :userId AND l.reply.id = :replyId")
    boolean existsByUserIdAndReplyId(@Param("userId") Long userId, @Param("replyId") Long replyId);

    // 대댓글 좋아요 수
    @Query("SELECT COUNT(l) FROM Like l WHERE l.reply.id = :replyId")
    long countByReplyId(@Param("replyId") Long replyId);

    // 대댓글 좋아요 삭제
    @Query("DELETE FROM Like l WHERE l.id.userId = :userId AND l.reply.id = :replyId")
    void deleteByUserIdAndReplyId(@Param("userId") Long userId, @Param("replyId") Long replyId);
}
