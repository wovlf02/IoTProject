package com.smartcampus.back.community.block.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.CommentBlock;
import com.smartcampus.back.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 댓글 차단 정보에 접근하기 위한 JPA Repository
 */
@Repository
public interface CommentBlockRepository extends JpaRepository<CommentBlock, Long> {

    /**
     * 현재 로그인 사용자가 특정 댓글을 차단했는지 여부
     */
    Optional<CommentBlock> findByUserAndComment(User user, Comment comment);

    /**
     * 특정 사용자가 차단한 댓글 목록 전체 조회
     */
    List<CommentBlock> findAllByUser(User user);

    /**
     * 특정 댓글에 대해 모든 차단 정보 삭제 (댓글 삭제 시)
     */
    void deleteAllByComment(Comment comment);
}
