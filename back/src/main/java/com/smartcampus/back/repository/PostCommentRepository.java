package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostComment;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PostCommentRepository (게시글 댓글 Repository)
 * post_comments 테이블과 매핑됨
 * 댓글 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    /**
     * 특정 게시글의 모든 댓글 조회
     * @param post 댓글이 작성된 게시글 (Post 엔티티)
     * @return 해당 게시글에 작성된 댓글 목록
     */
    List<PostComment> findByPost(Post post);

    /**
     * 특정 사용자가 작성한 댓글 목록 조회
     * @param user 댓글 작성자 (User 엔티티)
     * @return 해당 사용자가 작성한 댓글 목록
     */
    List<PostComment> findByUser(User user);

    /**
     * 특정 사용자의 모든 댓글 삭제
     * @param user 삭제할 댓글 작성자 (User 엔티티)
     */
    void deleteByUser(User user);

    /**
     * 특정 게시글의 모든 댓글 삭제
     * @param post 삭제할 게시글 (Post 엔티티)
     */
    void deleteByPost(Post post);
}
