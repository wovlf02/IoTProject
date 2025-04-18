package com.smartcampus.back.community.block.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.PostBlock;
import com.smartcampus.back.community.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 차단 정보에 접근하기 위한 JPA Repository
 */
@Repository
public interface PostBlockRepository extends JpaRepository<PostBlock, Long> {

    /**
     * 사용자가 특정 게시글을 차단했는지 여부
     */
    Optional<PostBlock> findByUserAndPost(User user, Post post);

    /**
     * 사용자가 차단한 게시글 목록 전체 조회
     */
    List<PostBlock> findAllByUser(User user);

    /**
     * 게시글 삭제 시 관련된 차단 정보 모두 제거
     */
    void deleteAllByPost(Post post);
}
