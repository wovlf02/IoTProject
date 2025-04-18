package com.smartcampus.back.community.block.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.ReplyBlock;
import com.smartcampus.back.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 대댓글 차단 정보에 접근하기 위한 JPA Repository
 */
@Repository
public interface ReplyBlockRepository extends JpaRepository<ReplyBlock, Long> {

    /**
     * 사용자가 특정 대댓글을 차단했는지 여부
     */
    Optional<ReplyBlock> findByUserAndReply(User user, Comment reply);

    /**
     * 사용자가 차단한 대댓글 목록 전체 조회
     */
    List<ReplyBlock> findAllByUser(User user);

    /**
     * 대댓글 삭제 시 관련 차단 정보 제거
     */
    void deleteAllByReply(Comment reply);
}
