package com.smartcampus.back.post.dto.comment;

import jakarta.validation.constraints.NotBlank;
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
}
