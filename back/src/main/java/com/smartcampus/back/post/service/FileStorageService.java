package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.attachment.FileDownloadResponse;
import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.enums.AttachmentTargetType;
import com.smartcampus.back.post.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * 첨부파일 처리 서비스 클래스
 * 로컬 파일 시스템 기반으로 게시글, 댓글, 대댓글의 첨부파일 업로드/다운로드/삭제 기능 제공
 */
@Service
@RequiredArgsConstructor
public class FileStorageService {

    /**
     * 루트 저장 경로 (application.yml의 file.upload-dir 또는 기본값 'uploads')
     */
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * 파일을 저장하고 Attachment 엔티티로 반환
     *
     * @param file        업로드된 MultipartFile
     * @param targetId    파일이 속한 대상 ID (게시글, 댓글, 대댓글 등)
     * @param targetType  대상 타입 (POST, COMMENT, REPLY)
     * @return Attachment 엔티티
     */
    public Attachment storeFile(MultipartFile file, Long targetId, AttachmentTargetType targetType) {
        try {
            // 1. 원본 파일 이름 검증 및 정제
            String originalNameRaw = file.getOriginalFilename();
            if (originalNameRaw == null || originalNameRaw.isBlank()) {
                throw new FileUploadException("파일 이름이 비어 있거나 null입니다.");
            }

            String originalName = StringUtils.cleanPath(originalNameRaw);
            String extension = getFileExtension(originalName);
            String storedName = UUID.randomUUID() + "." + extension;

            // 2. 저장 경로 생성: uploads/{targetType}/{targetId}/
            Path targetDir = Paths.get(uploadDir, targetType.name().toLowerCase(), targetId.toString())
                    .toAbsolutePath().normalize();
            Files.createDirectories(targetDir);

            // 3. 파일 저장
            Path targetPath = targetDir.resolve(storedName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 4. 첨부파일 엔티티 생성
            return Attachment.builder()
                    .fileName(originalName)
                    .storedName(storedName)
                    .filePath(targetPath.toString())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .targetId(targetId)
                    .targetType(targetType)
                    .build();

        } catch (IOException e) {
            throw new FileUploadException("파일 저장 중 오류 발생: " + e.getMessage(), e);
        }
    }

    /**
     * 첨부파일 엔티티를 다운로드용 DTO로 변환
     *
     * @param attachment 첨부파일 엔티티
     * @return FileDownloadResponse DTO
     */
    public FileDownloadResponse mapToDownloadResponse(Attachment attachment) {
        String downloadUrl = switch (attachment.getTargetType()) {
            case POST -> "/api/posts/" + attachment.getTargetId() + "/attachments/" + attachment.getId();
            case COMMENT -> "/api/posts/comments/" + attachment.getTargetId() + "/attachments/" + attachment.getId();
            case REPLY -> "/api/posts/replies/" + attachment.getTargetId() + "/attachments/" + attachment.getId();
        };

        return FileDownloadResponse.builder()
                .fileId(attachment.getId())
                .originFileName(attachment.getFileName())
                .storedFilePath(attachment.getFilePath())
                .fileExtension(getFileExtension(attachment.getFileName()))
                .fileSize(attachment.getFileSize())
                .downloadUrl(downloadUrl)
                .build();
    }

    /**
     * 저장된 파일을 Resource로 불러옴 (다운로드용)
     *
     * @param attachment 첨부파일 엔티티
     * @return FileSystemResource
     */
    public Resource loadFileAsResource(Attachment attachment) {
        return new FileSystemResource(Paths.get(attachment.getFilePath()));
    }

    /**
     * 실제 파일 시스템에서 파일 삭제
     *
     * @param attachment 삭제할 첨부파일
     */
    public void deleteFile(Attachment attachment) {
        try {
            Files.deleteIfExists(Paths.get(attachment.getFilePath()));
        } catch (IOException e) {
            System.err.println("⚠️ 파일 삭제 실패: " + e.getMessage());
        }
    }

    /**
     * 파일 이름에서 확장자 추출
     *
     * @param fileName 원본 파일 이름
     * @return 확장자 문자열 (없으면 "bin" 반환)
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < fileName.length() - 1)
                ? fileName.substring(dotIndex + 1)
                : "bin";
    }
}
