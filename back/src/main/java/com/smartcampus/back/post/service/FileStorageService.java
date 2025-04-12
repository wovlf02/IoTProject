package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.attachment.FileDownloadResponse;
import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.entity.Post;
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
 * 로컬 파일 시스템 기반의 첨부파일 저장 서비스입니다.
 * 업로드, 다운로드, 삭제 및 응답 DTO 매핑 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class FileStorageService {

    /**
     * 파일이 저장될 루트 디렉토리 (application.yml 또는 기본값: uploads)
     */
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * MultipartFile을 저장하고 Attachment 엔티티로 반환합니다.
     *
     * @param file 업로드된 파일
     * @param post 해당 게시글
     * @return 저장된 Attachment 엔티티
     */
    public Attachment storeFile(MultipartFile file, Post post) {
        try {
            // 1. 파일 이름 먼저 null-safe 추출
            String originalNameRaw = file.getOriginalFilename();
            if (originalNameRaw == null || originalNameRaw.isBlank()) {
                throw new FileUploadException("파일 이름이 비어 있거나 null입니다.");
            }

            // 2. 경로 정제
            String originalName = StringUtils.cleanPath(originalNameRaw);
            String extension = getFileExtension(originalName);
            String storedName = UUID.randomUUID().toString() + "." + extension;

            // 3. 저장 디렉토리 생성: uploads/{postId}/
            Path postDir = Paths.get(uploadDir, post.getId().toString()).toAbsolutePath().normalize();
            Files.createDirectories(postDir);

            // 4. 대상 경로 및 복사
            Path targetPath = postDir.resolve(storedName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 5. 엔티티 생성 및 반환 (toString 제거)
            return Attachment.builder()
                    .fileName(originalName)
                    .storedName(storedName)
                    .filePath(targetPath.toString()) // 이미 String이라 toString 불필요
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .post(post)
                    .build();

        } catch (IOException e) {
            throw new FileUploadException("파일 저장 중 오류 발생: " + e.getMessage(), e);
        }
    }


    /**
     * 첨부파일 정보를 다운로드 응답 DTO로 변환합니다.
     *
     * @param attachment 첨부파일 엔티티
     * @return FileDownloadResponse DTO
     */
    public FileDownloadResponse mapToDownloadResponse(Attachment attachment) {
        return FileDownloadResponse.builder()
                .fileId(attachment.getId())
                .originFileName(attachment.getFileName())
                .storedFilePath(attachment.getFilePath())
                .fileExtension(getFileExtension(attachment.getFileName()))
                .fileSize(attachment.getFileSize())
                .downloadUrl("/api/posts/" + attachment.getPost().getId() + "/attachments/" + attachment.getId())
                .build();
    }

    /**
     * 저장된 파일을 다운로드 가능한 Resource 객체로 반환합니다.
     *
     * @param attachment 첨부파일 엔티티
     * @return FileSystemResource 객체
     */
    public Resource loadFileAsResource(Attachment attachment) {
        Path filePath = Paths.get(attachment.getFilePath());
        return new FileSystemResource(filePath);
    }

    /**
     * 파일 시스템에서 첨부파일을 삭제합니다.
     *
     * @param attachment 삭제할 첨부파일
     */
    public void deleteFile(Attachment attachment) {
        try {
            Path path = Paths.get(attachment.getFilePath());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("⚠️ 파일 삭제 실패: " + e.getMessage());
        }
    }

    /**
     * 파일 이름에서 확장자를 추출합니다.
     *
     * @param fileName 원본 파일 이름
     * @return 확장자 문자열 (없으면 bin 반환)
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < fileName.length() - 1)
                ? fileName.substring(dotIndex + 1)
                : "bin";
    }
}
