package com.smartcampus.back.admin.service;

import com.smartcampus.back.admin.dto.request.UserResolveRequest;
import com.smartcampus.back.admin.dto.response.ReportListResponse;
import com.smartcampus.back.admin.repository.AdminReportRepository;
import com.smartcampus.back.community.entity.Report;
import com.smartcampus.back.community.service.CommentService;
import com.smartcampus.back.community.service.PostService;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 전용 신고 처리 서비스
 * - 게시글/댓글/사용자에 대한 신고 목록 조회
 * - 신고 처리 및 대상 리소스 숨김/정지 처리
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminReportService {

    private final AdminReportRepository adminReportRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 신고 목록 조회
     *
     * @param type 신고 유형 (POST, COMMENT, USER)
     * @param pageable 페이징 정보
     * @return 페이징된 신고 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    public ReportListResponse getReportList(String type, Pageable pageable) {
        return ReportListResponse.fromPage(
                adminReportRepository.findReportsByTypeAndStatus(type, "PENDING", pageable)
        );
    }

    /**
     * 신고 처리
     *
     * @param reportId 처리할 신고 ID
     * @param type 신고 대상 유형 (POST, COMMENT, USER)
     * @param request 처리 요청 DTO (조치 유형, 메모 포함)
     */
    public void resolveReport(Long reportId, String type, UserResolveRequest request) {
        Report report = adminReportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));

        if (report.isResolved()) {
            throw new CustomException(ErrorCode.REPORT_ALREADY_RESOLVED);
        }

        // 신고 상태 및 메모 기록
        report.resolve(request.getAction(), request.getMemo());

        // 신고 대상에 따라 실제 조치 수행
        switch (type) {
            case "POST" -> {
                if (report.getPostId() == null)
                    throw new CustomException(ErrorCode.INVALID_REPORT_TARGET);
                postService.hidePost(report.getPostId());
            }
            case "COMMENT" -> {
                if (report.getCommentId() == null)
                    throw new CustomException(ErrorCode.INVALID_REPORT_TARGET);
                commentService.hideComment(report.getCommentId());
            }
            case "USER" -> {
                if (report.getTargetUserId() == null)
                    throw new CustomException(ErrorCode.INVALID_REPORT_TARGET);
                userService.suspendUser(report.getTargetUserId());
            }
            default -> throw new CustomException(ErrorCode.INVALID_REPORT_TYPE);
        }
    }
}
