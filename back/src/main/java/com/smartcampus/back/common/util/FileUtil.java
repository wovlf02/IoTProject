package com.smartcampus.back.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 파일 저장, 이름 변경, 확장자 추출 등 파일 관련 유틸리티 클래스입니다.
 */
public class FileUtil {

    /**
     * 파일 확장자 추출
     *
     * @param filename 원본 파일 이름
     * @return 확장자 (예: "jpg", "pdf"), 없으면 빈 문자열
     */
    public static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex != -1) ? filename.substring(dotIndex + 1) : "";
    }

    /**
     * 랜덤 UUID 기반 저장 파일명 생성
     *
     * @param originalFilename 원본 파일 이름
     * @return 예: b23a17ee-1234-4d4b-bc4f-7b9c5a5a0d73.jpg
     */
    public static String generateSavedFilename(String originalFilename) {
        String ext = getExtension(originalFilename);
        return UUID.randomUUID().toString() + (ext.isEmpty() ? "" : "." + ext);
    }

    /**
     * 파일 저장 처리
     *
     * @param baseDir 저장될 기본 경로
     * @param file    MultipartFile 객체
     * @return 저장된 파일의 경로 (상대 경로 포함)
     */
    public static String saveFile(String baseDir, MultipartFile file) throws IOException {
        String savedName = generateSavedFilename(file.getOriginalFilename());
        String fullPath = Paths.get(baseDir, savedName).toString();

        File dest = new File(fullPath);
        dest.getParentFile().mkdirs(); // 폴더 없으면 생성
        file.transferTo(dest);

        return savedName;
    }

    /**
     * 파일 삭제
     *
     * @param path 삭제할 파일 전체 경로
     * @return 성공 여부
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    /**
     * 파일 크기를 KB 또는 MB 단위로 반환 (for logging or UI)
     */
    public static String getReadableFileSize(long sizeInBytes) {
        if (sizeInBytes >= 1024 * 1024) {
            return String.format("%.2f MB", sizeInBytes / (1024.0 * 1024));
        } else if (sizeInBytes >= 1024) {
            return String.format("%.2f KB", sizeInBytes / 1024.0);
        } else {
            return sizeInBytes + " B";
        }
    }
}
