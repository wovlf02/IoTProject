package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글(Comment) 관련 JPA Repository
 * 게시글에 작성된 댓글을 조회하거나 관리할 때 사용
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 게시글의 모든 댓글 조회
     *
     * @param postId 대상 게시글 ID
     * @return 댓글 리스트
     */
    List<Comment> findByPostId(Long postId);

    /**
     * 게시글 ID + 댓글 ID로 댓글 조회
     *
     * @param id 댓글 ID
     * @param postId 게시글 ID
     * @return 댓글 (Optional)
     */
    Comment findByIdAndPostId(Long id, Long postId);

    /**
     * 게시글 삭제 시 해당 게시글의 모든 댓글 삭제
     *
     * @param postId 게시글 ID
     */
    void deleteByPostId(Long postId);
}
