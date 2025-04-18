package com.smartcampus.back.community.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 수정 요청 DTO
 * <p>
 * 사용자가 게시글을 수정할 때 필요한 정보를 담고 있는 클래스입니다.
 * 제목, 내용, 카테고리, 첨부파일을 수정할 수 있습니다.
 * </p>
 */
@Getter
@Setter
public class PostUpdateRequest {

    /**
     * 수정할 게시글 제목
     */
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    /**
     * 수정할 게시글 본문 내용
     */
    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;

    /**
     * 변경할 게시글 카테고리 (선택)
     */
    private String category;

    /**
     * 추가 첨부파일 (선택)
     */
    private List<MultipartFile> newAttachments;

    /**
     * 삭제할 기존 첨부파일 ID 리스트 (선택)
     */
    private List<Long> deleteAttachmentIds;
}
