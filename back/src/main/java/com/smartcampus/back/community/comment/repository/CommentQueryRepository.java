package com.smartcampus.back.community.comment.repository;

import com.smartcampus.back.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 댓글/대댓글 계층형 조회를 위한 Repository
 * Querydsl 없이 @Query 기반으로 구현됨
 */
public interface CommentQueryRepository extends JpaRepository<Comment, Long> {

    /**
     * 게시글 ID에 해당하는 최상위 댓글 목록 조회
     * - 삭제되지 않았고
     * - 차단되지 않은 댓글만 포함
     * - 대댓글이 아닌 댓글만 (parent is null)
     *
     * @param postId 게시글 ID
     * @param userId 현재 사용자 ID (차단 필터링용)
     * @return 댓글 목록
     */
    @Query("""
        SELECT c FROM Comment c
        WHERE c.post.id = :postId
          AND c.parent IS NULL
          AND c.deleted = false
          AND NOT EXISTS (
              SELECT 1 FROM CommentBlock b
              WHERE b.comment.id = c.id
              AND b.user.id = :userId
          )
        ORDER BY c.createdAt ASC
    """)
    List<Comment> findParentCommentsByPostId(@Param("postId") Long postId, @Param("userId") Long userId);

    /**
     * 특정 댓글에 대한 대댓글 목록 조회
     * - 삭제되지 않았고
     * - 차단되지 않은 대댓글만 포함
     *
     * @param parentId 부모 댓글 ID
     * @param userId 현재 사용자 ID (차단 필터링용)
     * @return 대댓글 목록
     */
    @Query("""
        SELECT c FROM Comment c
        WHERE c.parent.id = :parentId
          AND c.deleted = false
          AND NOT EXISTS (
              SELECT 1 FROM CommentBlock b
              WHERE b.comment.id = c.id
              AND b.user.id = :userId
          )
        ORDER BY c.createdAt ASC
    """)
    List<Comment> findRepliesByParentId(@Param("parentId") Long parentId, @Param("userId") Long userId);
}
