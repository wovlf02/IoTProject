package com.smartcampus.back.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 파일 관련 유틸리티 클래스
 * <p>
 * 파일명 생성, 확장자 추출 등을 제공합니다.
 * </p>
 */
public class FileUtil {

    /**
     * MultipartFile로부터 원본 파일명 추출
     *
     * @param file 업로드된 파일
     * @return 원본 파일명
     */
    public static String getOriginalFileName(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return file.getOriginalFilename();
    }

    /**
     * 파일명으로부터 확장자 추출
     *
     * @param filename 파일명
     * @return 확장자 (예: jpg, png, pdf 등)
     */
    public static String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex >= 0) ? filename.substring(dotIndex + 1) : "";
    }

    /**
     * 랜덤 파일명 생성 (UUID 기반)
     *
     * @param originalFilename 원본 파일명
     * @return 랜덤 파일명 (UUID.확장자)
     */
    public static String generateRandomFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return extension.isEmpty() ? uuid : uuid + "." + extension;
    }

    /**
     * S3 또는 로컬에 저장할 경로 + 파일명 생성
     *
     * @param directory 저장할 디렉토리명 (ex: "attachments/chat")
     * @param originalFilename 원본 파일명
     * @return 디렉토리/랜덤파일명
     */
    public static String buildFilePath(String directory, String originalFilename) {
        String randomFileName = generateRandomFileName(originalFilename);
        return directory + "/" + randomFileName;
    }
}
