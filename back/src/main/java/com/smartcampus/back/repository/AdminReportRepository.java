package com.smartcampus.back.repository;

import com.smartcampus.back.entity.AdminReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminReportRepository extends JpaRepository<AdminReport, Long> {

    /**
     * 상태(PENDING, APPROVED 등)로 신고 내역 조회
     *
     * @param status 신고 상태
     * @return 해당 상태의 신고 리스트
     */
    List<AdminReport> findByStatus(String status);

    /**
     * 신고 대상 유형과 대상 ID로 신고 정보 조회
     *
     * @param targetType 신고 대상 유형
     * @param targetId 신고 대상 ID
     * @return 해당 조건의 신고 내역
     */
    Optional<AdminReport> findByTargetTypeAndTargetId(String targetType, Long targetId);
}
