package com.smartcampus.back.community.like.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.like.entity.Like;
import com.smartcampus.back.community.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 게시글 좋아요 여부 조회
     *
     * @param user 좋아요를 누른 사용자
     * @param post 대상 게시글
     * @return 좋아요가 존재하면 Optional 반환
     */
    Optional<Like> findByUserAndPost(User user, Post post);

    /**
     * 댓글 좋아요 여부 조회
     *
     * @param user   좋아요를 누른 사용자
     * @param comment 대상 댓글
     * @return 좋아요가 존재하면 Optional 반환
     */
    Optional<Like> findByUserAndComment(User user, Comment comment);

    /**
     * 대댓글 좋아요 여부 조회
     *
     * @param user  좋아요를 누른 사용자
     * @param reply 대상 대댓글
     * @return 좋아요가 존재하면 Optional 반환
     */
    Optional<Like> findByUserAndReply(User user, Comment reply);

    /**
     * 게시글에 대한 전체 좋아요 수 반환
     */
    long countByPost(Post post);

    /**
     * 댓글에 대한 전체 좋아요 수 반환
     */
    long countByComment(Comment comment);

    /**
     * 대댓글에 대한 전체 좋아요 수 반환
     */
    long countByReply(Comment reply);

    /**
     * 게시글 좋아요 삭제
     */
    void deleteByUserAndPost(User user, Post post);

    /**
     * 댓글 좋아요 삭제
     */
    void deleteByUserAndComment(User user, Comment comment);

    /**
     * 대댓글 좋아요 삭제
     */
    void deleteByUserAndReply(User user, Comment reply);
}
