package com.smartcampus.back.community.post.repository;

import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.entity.PostCategory;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 Repository
 *
 * 게시글 저장, 단건 조회, 카테고리별 목록 등 기본적인 CRUD 처리를 담당합니다.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 특정 사용자가 작성한 게시글 전체 조회
     *
     * @param writer 게시글 작성자
     * @return 해당 사용자가 작성한 게시글 목록
     */
    List<Post> findByWriter(User writer);

    /**
     * 카테고리별 게시글 조회
     *
     * @param category 게시글 카테고리
     * @return 해당 카테고리의 게시글 목록
     */
    List<Post> findByCategory(PostCategory category);

    /**
     * 삭제되지 않은 게시글 단건 조회
     *
     * @param id 게시글 ID
     * @return 게시글 (존재하지 않거나 삭제된 경우 Optional.empty)
     */
    Post findByIdAndDeletedFalse(Long id);
}
