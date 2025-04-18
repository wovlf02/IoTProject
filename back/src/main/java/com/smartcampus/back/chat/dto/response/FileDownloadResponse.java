package com.smartcampus.back.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 첨부파일 다운로드 응답 DTO
 *
 * 파일명, 크기, 타입, 다운로드 URL 등을 포함합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDownloadResponse {

    @Schema(description = "파일명", example = "lecture_note.pdf")
    private String fileName;

    @Schema(description = "파일 크기 (Byte)", example = "204800")
    private long fileSize;

    @Schema(description = "파일 MIME 타입", example = "application/pdf")
    private String contentType;

    @Schema(description = "파일 다운로드 URL", example = "/api/chat/messages/123/download")
    private String downloadUrl;

    @Schema(description = "미리보기 가능 여부", example = "true")
    private boolean previewAvailable;

    @Schema(description = "파일 미리보기용 URL", example = "/api/chat/messages/files/456")
    private String previewUrl;
}
