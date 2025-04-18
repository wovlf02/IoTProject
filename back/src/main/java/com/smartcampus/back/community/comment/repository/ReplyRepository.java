package com.smartcampus.back.community.comment.repository;

import com.smartcampus.back.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 대댓글(Reply) 전용 Repository
 *
 * Comment 엔티티를 기반으로 대댓글을 조회, 관리합니다.
 */
@Repository
public interface ReplyRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 댓글(parentCommentId)에 속한 대댓글 목록 조회
     *
     * @param parentCommentId 부모 댓글 ID
     * @return 대댓글 리스트
     */
    List<Comment> findByParentId(Long parentCommentId);

    /**
     * 게시글 ID 기반 전체 대댓글 조회
     *
     * @param postId 게시글 ID
     * @return 대댓글 리스트
     */
    List<Comment> findByPostIdAndParentIdIsNotNull(Long postId);
}
