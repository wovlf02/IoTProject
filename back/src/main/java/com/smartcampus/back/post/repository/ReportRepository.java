package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * @param targetId 신고 대상 ID
     * @param targetType 신고 대상 종류 (POST, COMMENT, REPLY)
     * @return 신고 존재 여부
     */
    boolean existsByReporterAndTargetIdAndTargetType(Long reporterId, Long targetId, String targetType);

    /**
     * 특정 대상에 대한 모든 신고 내역 조회
     *
     * @param targetId 신고 대상 ID
     * @param targetType 대상 타입
     * @return 신고 리스트
     */
    List<Report> findByTargetIdAndTargetType(Long targetId, String targetType);

    /**
     * 특정 게시글과 연결된 모든 신고 삭제
     *
     * @param postId 게시글 ID
     */
    void deleteByPostId(Long postId);
}
