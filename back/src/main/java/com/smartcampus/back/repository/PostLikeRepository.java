package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostLike;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PostLikeRepository (게시글 좋아요 Repository)
 * post_likes 테이블과 매핑됨
 * 추천 기능 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    /**
     * 특정 게시글에 추천을 누른 사용자 목록 조회
     * @param post 추천이 눌린 게시글 (Post 엔티티)
     * @return 해당 게시글에 추천을 누른 사용자 목록
     */
    long countByPost(Post post);

    /**
     * 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
     * @param post 확인할 게시글 (Post 엔티티)
     * @param user 확인할 사용자 (User 엔티티)
     * @return 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByPostAndUser(Post post, User user);

    /**
     * 특정 사용자가 특정 게시글의 추천을 취소 (삭제)
     * @param post 삭제할 게시글 (Post 엔티티)
     * @param user 추천을 취소할 사용자 (User 엔티티)
     */
    void deleteByPostAndUser(Post post, User user);
}
