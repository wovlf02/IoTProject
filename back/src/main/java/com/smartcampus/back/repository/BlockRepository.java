package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

    /**
     * 사용자가 특정 대상(targetType, targetId)을 차단했는지 여부 확인
     *
     * @param targetType 차단 대상 유형 (예: POST, COMMENT, USER)
     * @param targetId 차단 대상 ID
     * @param userId 차단자 사용자 ID
     * @return 차단 여부
     */
    boolean existsByTargetTypeAndTargetIdAndUserId(String targetType, Long targetId, Long userId);
}
