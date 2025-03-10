package com.smartcampus.back.repository;

import com.smartcampus.back.entity.CommentReply;
import com.smartcampus.back.entity.PostComment;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CommentReplyRepository (게시글 대댓글 Repository)
 * comment_replies 테이블과 매핑됨
 * 대댓글 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

    /**
     * 특정 댓글에 달린 모든 대댓글 조회
     * @param comment 부모 댓글 (PostComment 엔티티)
     * @return 해당 댓글의 대댓글 목록
     */
    List<CommentReply> findByComment(PostComment comment);

    /**
     * 특정 사용자가 작성한 대댓글 목록 조회
     * @param user 대댓글 작성자 (User 엔티티)
     * @return 해당 사용자가 작성한 대댓글 목록
     */
    List<CommentReply> findByUser(User user);

    /**
     * 특정 사용자의 모든 대댓글 삭제
     * @param user 삭제할 대댓글 작성자 (User 엔티티)
     */
    void deleteByUser(User user);

    /**
     * 특정 댓글에 달린 모든 대댓글 삭제
     * @param comment 삭제할 부모 댓글 (PostComment 엔티티)
     */
    void deleteByComment(PostComment comment);
}
