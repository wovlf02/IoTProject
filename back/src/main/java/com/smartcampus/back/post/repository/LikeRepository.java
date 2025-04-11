package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Like;
import com.smartcampus.back.post.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시글 좋아요 관련 JPA Repository
 * 사용자의 좋아요 기록을 관리하고, 게시글의 총 좋아요 수를 계산 가능
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    /**
     * 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
     * 
     * @param userId 사용자 ID
     * @param postId 게시글 ID
     * @return 존재 여부
     */
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    /**
     * 게시글에 대한 총 좋아요 수 반환
     * 
     * @param postId 게시글 ID
     * @return 좋아요 수
     */
    long countByPostId(Long postId);

    /**
     * 사용자 ID + 게시글 ID 기준으로 좋아요 삭제
     * 
     * @param userId 사용자 ID
     * @param postId 게시글 ID
     */
    void deleteByUserIdAndPostId(Long userId, Long postId);

    /**
     * 게시글 삭제 시 해당 게시글의 좋아요 전체 삭제
     * 
     * @param postId 게시글 ID
     */
    void deleteByPostId(Long postId);
}
