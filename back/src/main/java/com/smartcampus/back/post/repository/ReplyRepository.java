package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 대댓글(답글) 관련 JPA Repository
 * 댓글이 달린 답글(Reply)을 조회하거나 삭제하는 데 사용됨
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 특정 댓글에 달린 모든 대댓글 조회
     *
     * @param commentId 대상 댓글 ID
     * @return 대댓글 리스트
     */
    List<Reply> findByCommentId(Long commentId);

    /**
     * 댓글 ID + 대댓글 ID로 해당 대댓글 조회
     *
     * @param id 대댓글 ID
     * @param commentId 댓글 ID
     * @return 해당 대댓글
     */
    Reply findByIdAndCommentId(Long id, Long commentId);

    /**
     * 댓글 삭제 시 해당 댓글의 모든 대댓글 삭제
     *
     * @param commentId 댓글 ID
     */
    void deleteByCommentId(Long commentId);
}
