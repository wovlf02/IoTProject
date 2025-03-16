package com.smartcampus.back.repository;

import com.smartcampus.back.entity.CommentReply;
import com.smartcampus.back.entity.PostComment;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CommentReplyRepository 인터페이스
 *
 * comment_replies 테이블과 매핑된 CommentReply 엔티티의 데이터 접근 계층
 * 대댓글 저장, 삭제, 조회 기능 포함
 */
@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

    /**
     * 특정 댓글에 속한 모든 대댓글 조회 (최신순)
     * 
     * comment_id를 기준으로 해당 댓글의 모든 대댓글을 조회
     * 최신순 정렬
     * 
     * @param comment 대상 댓글
     * @return 특정 댓글에 대한 대댓글 목록
     */
    List<CommentReply> findByCommentOrderByCreatedAtDesc(PostComment comment);

    /**
     * 특정 사용자가 작성한 대댓글 조회
     * 
     * user_id를 기준으로 해당 사용자가 작성한 모든 대댓글을 조회
     * 최신순 정렬
     * 
     * @param user 대댓글 작성자
     * @return 사용자가 작성한 대댓글 목록
     */
    List<CommentReply> findByUserOrderByCreatedAtDesc(User user);

    /**
     * 특정 댓글의 대댓글 개수 조회
     * 
     * comment_id를 기준으로 해당 댓글의 대댓글 개수를 반환
     * 
     * @param comment 대상 댓글
     * @return 특정 댓글의 촏 대댓글 수
     */
    long countByComment(PostComment comment);
}
