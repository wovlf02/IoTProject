package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 신고(Report) 관련 JPA Repository
 * 게시글, 댓글, 대댓글에 대한 사용자 신고 정보를 관리
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * 특정 사용자, 대상 ID, 대상 타입 조합으로 신고 이력 존재 여부 확인
     *
     * @param reporterId 신고자 ID
     * @param targetId   신고 대상 ID
     * @param targetType 대상 타입 (POST, COMMENT, REPLY)
     * @return 이미 신고한 경우 true
     */
    boolean existsByReporterAndTargetIdAndTargetType(Long reporterId, Long targetId, String targetType);

    /**
     * 특정 대상에 대한 모든 신고 조회
     *
     * @param targetId   대상 ID
     * @param targetType 대상 타입 (POST, COMMENT, REPLY)
     * @return 해당 대상에 대한 신고 목록
     */
    List<Report> findByTargetIdAndTargetType(Long targetId, String targetType);

    /**
     * 특정 게시글과 연결된 신고 전부 삭제
     *
     * @param postId 게시글 ID
     */
    @Modifying
    @Transactional
    void deleteByPost_PostId(Long postId); // 연관 엔티티의 post.id로 안전하게 참조
}
