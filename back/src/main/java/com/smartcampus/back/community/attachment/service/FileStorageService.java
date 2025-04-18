package com.smartcampus.back.community.attachment.service;

import com.smartcampus.back.community.attachment.entity.Attachment;
import com.smartcampus.back.community.attachment.repository.AttachmentRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.common.util.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 첨부파일 저장 및 다운로드를 처리하는 로컬 파일 시스템 기반 서비스
 */
@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${file.upload-dir:/uploads}") // 기본 경로 설정
    private String rootDir;

    private final AttachmentRepository attachmentRepository;

    /**
     * 실제 파일 저장
     *
     * @return 저장된 파일명 (UUID.확장자)
     */
    public String save(MultipartFile file, Object post, Object comment, Object reply) throws IOException {
        String savedName = FileUtil.generateSavedFilename(file.getOriginalFilename());
        String relativeDir = resolveUploadDirectory(post, comment, reply);

        String fullPath = Paths.get(rootDir, relativeDir, savedName).toString();
        File targetFile = new File(fullPath);
        targetFile.getParentFile().mkdirs();

        file.transferTo(targetFile);

        return savedName;
    }

    /**
     * 전체 경로 생성
     */
    public String buildFullPath(Object post, Object comment, Object reply, String savedName) {
        String relativeDir = resolveUploadDirectory(post, comment, reply);
        return Paths.get(rootDir, relativeDir, savedName).toString();
    }

    /**
     * 물리 파일 삭제
     */
    public void deletePhysicalFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.delete()) {
            throw new CustomException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    /**
     * 첨부파일 다운로드
     */
    public void downloadFile(Long attachmentId, HttpServletResponse response) throws IOException {
        Attachment attachment = getAttachmentOrThrow(attachmentId);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");

        try (InputStream in = new FileInputStream(attachment.getFilePath())) {
            StreamUtils.copy(in, response.getOutputStream());
        }
    }

    /**
     * 이미지 미리보기 (image/* Content-Type)
     */
    public void previewImage(Long attachmentId, HttpServletResponse response) throws IOException {
        Attachment attachment = getAttachmentOrThrow(attachmentId);

        if (!attachment.getContentType().startsWith("image")) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE, "이미지 파일이 아닙니다.");
        }

        response.setContentType(attachment.getContentType());

        try (InputStream in = new FileInputStream(attachment.getFilePath())) {
            StreamUtils.copy(in, response.getOutputStream());
        }
    }

    /**
     * 저장 위치 결정 (post/comment/reply 중 하나)
     */
    private String resolveUploadDirectory(Object post, Object comment, Object reply) {
        if (post != null) return "post";
        if (comment != null) return "comment";
        if (reply != null) return "reply";
        return "misc";
    }

    private Attachment getAttachmentOrThrow(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
    }
}
