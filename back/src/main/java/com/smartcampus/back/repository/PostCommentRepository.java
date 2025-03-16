package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostComment;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PostCommentRepository 인터페이스
 *
 * post_comments 테이블과 매핑된 PostComment 엔티티의 데이터 접근 계층
 * 게시글 댓글 저장, 삭제, 조회 기능 포함
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    /**
     * 특정 게시글의 모든 댓글 조회 (최신순)
     *
     * post_id를 기준으로 해당 게시글의 모든 댓글을 조회
     * 최신 댓글이 가장 위에 오도록 정렬
     *
     * @param post 대상 게시글
     * @return 특정 게시글에 달린 댓글 목록
     */
    List<PostComment> findByPostOrderByCreatedAtDesc(Post post);

    /**
     * 특정 사용자가 작성한 댓글 조회
     *
     * user_id를 기준으로 해당 사용자가 작성한 모든 댓글을 조회
     * 최신 댓글이 가장 위에 오도록 정렬
     *
     * @param user 댓글 작성자
     * @return 사용자가 작성한 댓글 목록
     */
    List<PostComment> findByUserOrderByCreatedAtDesc(User user);

    /**
     * 특정 게시글의 댓글 개수 조회
     *
     * post_id를 기준으로 해당 게시글의 댓글 개수를 반환
     * @param post 대상 게시글
     * @return 특정 게시글의 총 댓글 수
     */
    long countByPost(Post post);
}
