package com.smartcampus.back.community.attachment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 첨부파일 목록 응답 DTO
 * <p>
 * 게시글, 댓글, 대댓글 등에 첨부된 모든 파일 정보를 리스트 형태로 반환합니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class AttachmentListResponse {

    /**
     * 첨부파일 리스트
     */
    private List<AttachmentResponse> attachments;

    /**
     * 총 첨부파일 수
     */
    private int total;

    public static AttachmentListResponse of(List<AttachmentResponse> attachments) {
        return AttachmentListResponse.builder()
                .attachments(attachments)
                .total(attachments.size())
                .build();
    }
}
