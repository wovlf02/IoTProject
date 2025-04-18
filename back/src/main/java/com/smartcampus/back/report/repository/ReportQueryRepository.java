package com.smartcampus.back.report.repository;

import com.smartcampus.back.report.dto.response.ReportSummary;
import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ReportQueryRepository extends Repository<com.smartcampus.back.report.entity.Report, Long> {

    /**
     * 관리자용 신고 목록 조회 (type + status 동적 필터링)
     *
     * @param type 신고 대상 타입 (nullable)
     * @param status 신고 상태 (nullable)
     * @return 신고 요약 리스트
     */
    @Query("""
        SELECT new com.smartcampus.back.report.dto.response.ReportSummary(
            r.id,
            COALESCE(r.post.id, r.comment.id, r.reply.id, r.targetUser.id),
            r.type,
            r.status,
            r.reporter.id,
            r.reason,
            r.createdAt
        )
        FROM Report r
        WHERE (:type IS NULL OR r.type = :type)
          AND (:status IS NULL OR r.status = :status)
        ORDER BY r.createdAt DESC
    """)
    List<ReportSummary> findAllByTypeAndStatus(
            @Param("type") ReportType type,
            @Param("status") ReportStatus status
    );
}
