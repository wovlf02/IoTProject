package com.smartcampus.back.repository;

import com.smartcampus.back.entity.CommunityReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityReportRepository extends JpaRepository<CommunityReport, Long> {

    /**
     * 신고 대상 유형과 ID로 신고 내역 단건 조회
     *
     * @param targetType 신고 대상 유형 (POST, COMMENT, USER)
     * @param targetId 신고 대상 ID
     * @return 신고 내역
     */
    Optional<CommunityReport> findByTargetTypeAndTargetId(String targetType, Long targetId);

    /**
     * 사용자 ID로 본인이 작성한 신고 내역 전체 조회
     *
     * @param reporterId 신고자 ID
     * @return 해당 사용자가 작성한 신고 리스트
     */
    List<CommunityReport> findByReporterId(Long reporterId);
}
