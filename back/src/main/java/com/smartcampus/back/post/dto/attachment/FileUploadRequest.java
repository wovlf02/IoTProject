package com.smartcampus.back.post.dto.attachment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 첨부파일 업로드 요청 DTO
 * 게시글 또는 댓글에 첨부되는 파일을 전송할 때 사용
 */
@Getter
@Setter
public class FileUploadRequest {

    /**
     * 업로드할 파일 (단일 파일 전송)
     */
    private MultipartFile file;

    /**
     * 파일을 첨부할 대상 게시글 ID
     */
    private Long postId;

    /**
     * 파일을 첨부할 대상 댓글 ID (선택적)
     */
    private Long commentId;

    /**
     * 파일 업로드한 사용자 ID
     */
    private Long uploaderId;
}
