package com.smartcampus.back.admin.repository;

import com.smartcampus.back.community.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 관리자 전용 신고 처리 레포지토리
 * - 게시글, 댓글, 사용자 신고 조회
 * - 처리 여부 및 유형 필터링 가능
 */
@Repository
public interface AdminReportRepository extends JpaRepository<Report, Long> {

    /**
     * 신고 목록 페이징 조회
     *
     * @param type 신고 유형 (POST, COMMENT, USER 등)
     * @param status 처리 상태 (e.g., PENDING, RESOLVED)
     * @param pageable 페이징 조건
     * @return 조건에 맞는 신고 목록 페이지
     */
    @Query("SELECT r FROM Report r " +
            "WHERE (:type IS NULL OR r.reportType = :type) " +
            "AND (:status IS NULL OR r.status = :status)")
    Page<Report> findReportsByTypeAndStatus(@Param("type") String type,
                                            @Param("status") String status,
                                            Pageable pageable);
}
