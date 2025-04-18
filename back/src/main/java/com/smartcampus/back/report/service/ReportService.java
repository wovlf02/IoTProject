package com.smartcampus.back.report.service;

import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.report.dto.request.ReportRequest;
import com.smartcampus.back.report.dto.response.ReportResponse;
import com.smartcampus.back.report.entity.Report;
import com.smartcampus.back.report.entity.ReportStatus;
import com.smartcampus.back.report.entity.ReportType;
import com.smartcampus.back.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 신고 요청을 처리하는 서비스 클래스
 * <p>
 * 게시글, 댓글, 대댓글, 사용자에 대한 신고 기능을 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    /**
     * 게시글에 대한 신고를 처리합니다.
     *
     * @param postId  신고 대상 게시글 ID
     * @param request 신고 요청 데이터
     * @return 신고 응답 DTO
     */
    @Transactional
    public ReportResponse reportPost(Long postId, ReportRequest request) {
        if (reportRepository.existsByReporterIdAndPostId(request.getReporterId(), postId)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Report report = Report.builder()
                .reporterId(request.getReporterId())
                .postId(postId)
                .reason(request.getReason())
                .type(ReportType.POST)
                .status(ReportStatus.PENDING)
                .build();

        reportRepository.save(report);
        return ReportResponse.of(report.getId(), "게시글 신고가 접수되었습니다.");
    }

    /**
     * 댓글에 대한 신고를 처리합니다.
     *
     * @param commentId 댓글 ID
     * @param request   신고 요청 데이터
     * @return 신고 응답 DTO
     */
    @Transactional
    public ReportResponse reportComment(Long commentId, ReportRequest request) {
        if (reportRepository.existsByReporterIdAndCommentId(request.getReporterId(), commentId)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Report report = Report.builder()
                .reporterId(request.getReporterId())
                .commentId(commentId)
                .reason(request.getReason())
                .type(ReportType.COMMENT)
                .status(ReportStatus.PENDING)
                .build();

        reportRepository.save(report);
        return ReportResponse.of(report.getId(), "댓글 신고가 접수되었습니다.");
    }

    /**
     * 대댓글(Reply)에 대한 신고를 처리합니다.
     *
     * @param replyId  대댓글 ID
     * @param request  신고 요청 데이터
     * @return 신고 응답 DTO
     */
    @Transactional
    public ReportResponse reportReply(Long replyId, ReportRequest request) {
        if (reportRepository.existsByReporterIdAndReplyId(request.getReporterId(), replyId)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Report report = Report.builder()
                .reporterId(request.getReporterId())
                .replyId(replyId)
                .reason(request.getReason())
                .type(ReportType.REPLY)
                .status(ReportStatus.PENDING)
                .build();

        reportRepository.save(report);
        return ReportResponse.of(report.getId(), "대댓글 신고가 접수되었습니다.");
    }

    /**
     * 사용자에 대한 신고를 처리합니다.
     *
     * @param targetUserId 신고 대상 사용자 ID
     * @param request      신고 요청 데이터
     * @return 신고 응답 DTO
     */
    @Transactional
    public ReportResponse reportUser(Long targetUserId, ReportRequest request) {
        if (reportRepository.existsByReporterIdAndTargetUserId(request.getReporterId(), targetUserId)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Report report = Report.builder()
                .reporterId(request.getReporterId())
                .targetUserId(targetUserId)
                .reason(request.getReason())
                .type(ReportType.USER)
                .status(ReportStatus.PENDING)
                .build();

        reportRepository.save(report);
        return ReportResponse.of(report.getId(), "사용자 신고가 접수되었습니다.");
    }
}
