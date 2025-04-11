package com.smartcampus.back.post.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 생성 요청 DTO
 * 사용자가 새 게시글을 작성할 때 전송하는 요청 데이터
 */
@Getter
@Setter
public class PostCreateRequest {

    /**
     * 게시글 제목
     */
    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    /**
     * 게시글 본문 내용
     */
    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    /**
     * 작성자 ID
     */
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long writerId;

    /**
     * 게시글 공개 여부 (true: 공개 / false: 비공개)
     */
    private boolean isPublic = true;

    /**
     * 첨부파일 리스트 (선택 사항)
     */
    private List<MultipartFile> files;
}
