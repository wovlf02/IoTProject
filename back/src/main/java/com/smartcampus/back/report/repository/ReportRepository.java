package com.smartcampus.back.report.repository;

import com.smartcampus.back.report.entity.Report;
import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * 동일한 신고자가 동일 대상에 대해 이미 신고한 내역이 있는지 확인
     *
     * @param reporterId  신고자 ID
     * @param targetId    신고 대상 ID
     * @param type        신고 대상 타입 (POST, COMMENT, REPLY, USER)
     * @return Optional<Report> 존재 시 중복 신고
     */
    Optional<Report> findByReporterIdAndTargetIdAndType(Long reporterId, Long targetId, ReportType type);

    /**
     * 신고 상태 기준으로 카운트 (선택적 활용)
     *
     * @param status 신고 처리 상태
     * @return 해당 상태의 신고 수
     */
    Long countByStatus(ReportStatus status);
}
