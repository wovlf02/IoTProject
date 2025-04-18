package com.smartcampus.back.community.comment.repository;

import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 및 대댓글에 대한 JPA 기반 CRUD 리포지토리
 * 기본적인 저장, 삭제, 게시글 기반 조회 기능 제공
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 게시글 ID로 모든 댓글 조회
     * (대댓글 포함, 삭제된 댓글 제외)
     */
    List<Comment> findAllByPostAndDeletedFalse(Post post);

    /**
     * 사용자 ID 기준으로 작성한 댓글 전체 조회
     */
    List<Comment> findByUser(User user);

    /**
     * 부모 댓글 ID 기준으로 자식 댓글(대댓글) 조회
     */
    List<Comment> findByParent(Comment parent);
}
