package com.smartcampus.back.community.comment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentRepository;
import com.smartcampus.back.community.report.entity.Report;
import com.smartcampus.back.community.report.entity.ReportStatus;
import com.smartcampus.back.community.report.entity.ReportType;
import com.smartcampus.back.community.report.repository.ReportRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 댓글 및 대댓글에 대한 신고 처리를 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    /**
     * 댓글 또는 대댓글에 대한 신고 요청을 처리합니다.
     *
     * @param user 신고자
     * @param commentId 신고 대상 댓글 ID
     * @param reason 신고 사유
     */
    @Transactional
    public void reportComment(User user, Long commentId, String reason) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "신고 대상 댓글이 존재하지 않습니다."));

        // 본인 신고 불가
        if (comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "자기 자신의 댓글은 신고할 수 없습니다.");
        }

        // 중복 신고 방지
        boolean alreadyReported = reportRepository.existsByReporterAndComment(user, comment);
        if (alreadyReported) {
            throw new CustomException(ErrorCode.DUPLICATE_REQUEST, "이미 신고한 댓글입니다.");
        }

        Report report = Report.builder()
                .reporter(user)
                .comment(comment)
                .targetUser(comment.getUser())
                .reason(reason)
                .type(ReportType.COMMENT)
                .status(ReportStatus.PENDING)
                .build();

        reportRepository.save(report);
    }
}
