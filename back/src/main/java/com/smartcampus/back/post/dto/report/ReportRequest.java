package com.smartcampus.back.post.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글/댓글/대댓글 신고 요청 DTO
 * 클라이언트가 신고를 요청할 때 사용하는 구조
 */
@Getter
@Setter
public class ReportRequest {

    /**
     * 신고자 사용자 ID
     */
    @NotNull(message = "신고자 ID는 필수입니다.")
    private Long reporterId;

    /**
     * 신고 사유 (자유 텍스트)
     */
    @NotBlank(message = "신고 사유는 필수입니다.")
    private String reason;
}
