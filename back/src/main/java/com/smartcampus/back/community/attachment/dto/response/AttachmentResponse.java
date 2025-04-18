package com.smartcampus.back.community.attachment.dto.response;

import com.smartcampus.back.community.attachment.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 개별 첨부파일 응답 DTO
 * <p>
 * 첨부파일의 ID, 파일명, MIME 타입, 다운로드/미리보기 URL 등을 포함합니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class AttachmentResponse {

    private Long id;              // 첨부파일 고유 ID
    private String fileName;      // 원본 파일명
    private String contentType;   // MIME 타입 (image/png, application/pdf 등)
    private long fileSize;        // 바이트 단위 크기
    private String downloadUrl;   // 다운로드용 URL
    private String previewUrl;    // 미리보기 이미지 URL (선택적으로 사용)

    /**
     * Entity → DTO 변환 메서드
     *
     * @param attachment Attachment 엔티티
     * @return DTO
     */
    public static AttachmentResponse from(Attachment attachment) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getOriginalName())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getSize())
                .downloadUrl("/api/community/attachments/" + attachment.getId() + "/download")
                .previewUrl(isImage(attachment.getContentType())
                        ? "/api/community/attachments/" + attachment.getId() + "/preview"
                        : null)
                .build();
    }

    /**
     * 이미지 형식 여부 판단
     */
    private static boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image");
    }
}
