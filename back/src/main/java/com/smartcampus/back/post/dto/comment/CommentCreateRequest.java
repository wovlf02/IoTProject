package com.smartcampus.back.post.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 생성 요청 DTO
 * 게시글에 새로운 댓글을 작성할 때 클라이언트가 전송하는 데이터
 */
@Getter
@Setter
public class CommentCreateRequest {

    /**
     * 댓글 내용
     */
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    /**
     * 댓글 작성자 ID
     */
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long writerId;
}
