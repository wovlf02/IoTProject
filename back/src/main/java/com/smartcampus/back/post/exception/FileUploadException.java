package com.smartcampus.back.post.exception;

/**
 * 첨부파일 업로드 중 발생하는 예외
 */
public class FileUploadException extends RuntimeException {

    public FileUploadException() {
        super("파일 업로드 중 오류가 발생했습니다.");
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
