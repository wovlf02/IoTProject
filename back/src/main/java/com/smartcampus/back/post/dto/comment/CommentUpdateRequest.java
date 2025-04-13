package com.smartcampus.back.post.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 수정 요청 DTO
 * 기존 댓글의 내용을 수정할 때 사용
 */
@Getter
@Setter
public class CommentUpdateRequest {

    /**
     * 수정할 댓글 내용
     */
    @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
    private String content;

    /**
     * 요청자(작성자) ID - 수정 권한 확인용
     */
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long writerId;
}
