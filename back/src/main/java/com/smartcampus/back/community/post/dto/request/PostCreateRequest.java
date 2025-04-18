package com.smartcampus.back.community.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 작성 요청 DTO
 * <p>
 * 사용자가 게시글을 작성할 때 필요한 정보를 담고 있는 클래스입니다.
 * 제목, 내용, 카테고리, 첨부파일 등을 포함합니다.
 * </p>
 */
@Getter
@Setter
public class PostCreateRequest {

    /**
     * 게시글 제목
     */
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    /**
     * 게시글 본문 내용
     */
    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;

    /**
     * 게시글 카테고리 (예: FREE, QNA, NOTICE 등)
     */
    @NotNull(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

    /**
     * 첨부파일 리스트 (선택)
     */
    private List<MultipartFile> attachments;
}
