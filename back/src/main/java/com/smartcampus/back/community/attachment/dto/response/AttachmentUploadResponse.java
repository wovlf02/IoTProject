package com.smartcampus.back.community.attachment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 첨부파일 업로드 응답 DTO
 * <p>
 * 업로드된 파일 수, 메시지, 업로드된 파일 ID 리스트 등을 포함하여 응답합니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class AttachmentUploadResponse {

    /**
     * 업로드 완료 메시지
     */
    private String message;

    /**
     * 업로드된 파일 수
     */
    private int uploaded;

    /**
     * 업로드된 파일 ID 목록
     */
    private List<Long> uploadedFileIds;

    /**
     * 정적 팩토리 메서드
     */
    public static AttachmentUploadResponse of(List<Long> fileIds) {
        return AttachmentUploadResponse.builder()
                .message("첨부파일이 업로드되었습니다.")
                .uploaded(fileIds.size())
                .uploadedFileIds(fileIds)
                .build();
    }
}
