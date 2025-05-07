package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 사용자가 특정 대상(게시글 또는 댓글)에 좋아요를 눌렀는지 확인
     *
     * @param targetType 대상 유형 (POST, COMMENT 등)
     * @param targetId 대상 ID
     * @param userId 사용자 ID
     * @return 존재 여부
     */
    boolean existsByTargetTypeAndTargetIdAndUserId(String targetType, Long targetId, Long userId);

    /**
     * 특정 대상에 대한 좋아요 수 조회
     *
     * @param targetType 대상 유형
     * @param targetId 대상 ID
     * @return 좋아요 개수
     */
    long countByTargetTypeAndTargetId(String targetType, Long targetId);
}
