package com.smartcampus.back.post.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 수정 요청 DTO
 * 제목, 내용, 공개 여부, 첨부파일 추가/삭제, 작성자 ID 포함
 */
@Getter
@Setter
public class PostUpdateRequest {

    /**
     * 수정할 제목
     */
    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    /**
     * 수정할 내용
     */
    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;

    /**
     * 공개 여부 변경
     */
    private boolean isPublic;

    /**
     * 새로 추가할 첨부파일 목록
     */
    private List<MultipartFile> newFiles;

    /**
     * 삭제할 첨부파일 ID 목록
     */
    private List<Long> deleteFiles;

    /**
     * 작성자 ID (수정 권한 검증용)
     */
    private Long writerId;
}
