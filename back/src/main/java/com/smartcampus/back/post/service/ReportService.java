package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.report.ReportRequest;
import com.smartcampus.back.post.dto.report.ReportResponse;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.entity.Report;
import com.smartcampus.back.post.enums.ReportTargetType;
import com.smartcampus.back.post.enums.ReportType;
import com.smartcampus.back.post.exception.CommentNotFoundException;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.exception.ReplyNotFoundException;
import com.smartcampus.back.post.repository.CommentRepository;
import com.smartcampus.back.post.repository.PostRepository;
import com.smartcampus.back.post.repository.ReplyRepository;
import com.smartcampus.back.post.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReportResponse reportPost(Long postId, ReportRequest request) {
        return handleReport(postId, ReportTargetType.POST, request);
    }

    @Transactional
    public ReportResponse reportComment(Long commentId, ReportRequest request) {
        return handleReport(commentId, ReportTargetType.COMMENT, request);
    }

    @Transactional
    public ReportResponse reportReply(Long replyId, ReportRequest request) {
        return handleReport(replyId, ReportTargetType.REPLY, request);
    }

    @Transactional
    public ReportResponse handleReport(Long targetId, ReportTargetType targetType, ReportRequest request) {
        validateReportReason(request.getType(), request.getReason());
        checkAlreadyReported(targetId, targetType, request.getReporterId());
        validateTargetExistsOrThrow(targetId, targetType);

        Report report = Report.builder()
                .targetId(targetId)
                .targetType(targetType.name())
                .reporterId(request.getReporterId())
                .type(request.getType())
                .reason(request.getReason() != null ? request.getReason().trim() : null)
                .build();

        if (targetType == ReportTargetType.POST) {
            Post post = postRepository.findById(targetId)
                    .orElseThrow(() -> new PostNotFoundException("신고 대상 게시글이 존재하지 않습니다."));
            report.setPost(post);
        }

        Report saved = reportRepository.save(report);

        return ReportResponse.builder()
                .reportId(saved.getId())
                .targetId(targetId)
                .targetType(targetType.name())
                .reportType(saved.getType())
                .message("신고가 성공적으로 접수되었습니다.")
                .build();
    }

    /**
     * 신고 유형이 OTHER인 경우만 reason 필수
     */
    private void validateReportReason(ReportType type, String reason) {
        if (type == ReportType.OTHER && (reason == null || reason.trim().isEmpty())) {
            throw new IllegalArgumentException("기타(OTHER) 유형은 신고 사유를 반드시 입력해야 합니다.");
        }
    }

    @Transactional(readOnly = true)
    public void checkAlreadyReported(Long targetId, ReportTargetType targetType, Long reporterId) {
        boolean exists = reportRepository.existsByReporterIdAndTargetIdAndTargetType(
                reporterId, targetId, targetType.name()
        );
        if (exists) {
            throw new IllegalStateException("이미 신고한 대상입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void validateTargetExistsOrThrow(Long targetId, ReportTargetType targetType) {
        switch (targetType) {
            case POST -> {
                if (!postRepository.existsById(targetId)) {
                    throw new PostNotFoundException("신고 대상 게시글이 존재하지 않습니다.");
                }
            }
            case COMMENT -> {
                if (!commentRepository.existsById(targetId)) {
                    throw new CommentNotFoundException("신고 대상 댓글이 존재하지 않습니다.");
                }
            }
            case REPLY -> {
                if (!replyRepository.existsById(targetId)) {
                    throw new ReplyNotFoundException("신고 대상 대댓글이 존재하지 않습니다.");
                }
            }
        }
    }
}
