package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostRecommendation;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PostRecommendationRepository 인터페이스
 *
 * post_recommendations 테이블과 매핑된 PostRecommendation 엔티티의 데이터 접근 계층
 * 게시글 추천(좋아요), 저장, 삭제, 조회 기능 포함
 */
@Repository
public interface PostRecommendationRepository extends JpaRepository<PostRecommendation, Long> {

    /**
     * 특정 사용자가 특정 게시글에 추천(좋아요)을 눌렀는지 확인
     *
     * user_id와 post_id를 기준으로 조회
     *
     * @param user 추천을 누른 사용자
     * @param post 추천받은 게시글
     * @return Optional<PostRecommendation> 객체 (추천이 존재하면 PostRecommendation, 없으면 empty)
     */
    Optional<PostRecommendation> findByUserAndPost(User user, Post post);

    /**
     * 특정 게시글의 총 추천(좋아요) 수 조회
     *
     * 특정 게시글(post_id)에 대한 추천 개수 반환
     * @param post 추천받은 게시글
     * @return 추천(좋아요) 개수
     */
    long countByPost(Post post);

    /**
     * 특정 사용자가 특정 게시글의 추천(좋아요)을 취소
     *
     * user_id와 post_id를 기반으로 데이터 삭제
     *
     * @param user 추천을 취소하는 사용자
     * @param post 추천을 취소할 게시글
     */
    void deleteByUserAndPost(User user, Post post);
}
