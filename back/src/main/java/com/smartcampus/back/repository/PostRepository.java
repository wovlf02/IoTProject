package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 작성자 ID로 게시글 목록 조회
     *
     * @param authorId 작성자 ID
     * @return 해당 사용자가 작성한 게시글 리스트
     */
    List<Post> findByAuthorId(Long authorId);

    /**
     * 전체 게시글을 작성일 기준 최신순으로 조회
     *
     * @return 게시글 리스트
     */
    List<Post> findAllByOrderByCreatedAtDesc();
}
