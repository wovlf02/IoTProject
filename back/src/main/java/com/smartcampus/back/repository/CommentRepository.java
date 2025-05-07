package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 게시글 ID로 댓글 전체 조회
     *
     * @param postId 게시글 ID
     * @return 해당 게시글의 댓글 목록
     */
    List<Comment> findByPostId(Long postId);

    /**
     * 부모 댓글 ID로 대댓글 조회
     *
     * @param parentCommentId 부모 댓글 ID
     * @return 대댓글 목록
     */
    List<Comment> findByParentCommentId(Long parentCommentId);
}
