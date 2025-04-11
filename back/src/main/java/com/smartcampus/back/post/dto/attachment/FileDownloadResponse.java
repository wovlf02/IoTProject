package com.smartcampus.back.post.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 첨부파일 다운로드 응답 DTO
 * 클라이언트가 파일 정보를 미리 조회하거나, 다운로드할 때 메타 정보를 제공
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadResponse {

    /**
     * 파일 고유 ID
     */
    private Long fileId;

    /**
     * 원본 파일 이름
     */
    private String originFileName;

    /**
     * 저장된 서버 상의 파일 경로
     */
    private String storedFilePath;

    /**
     * 파일 확장자 (ex. .jpa, .pdf 등)
     */
    private String fileExtension;

    /**
     * 파일 크기 (byte 단위)
     */
    private long fileSize;

    /**
     * 다운로드 URL (정적 URL or presigned URL)
     */
    private String downloadUrl;
}
