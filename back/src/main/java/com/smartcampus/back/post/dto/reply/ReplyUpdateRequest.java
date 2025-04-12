package com.smartcampus.back.post.dto.reply;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 대댓글 수정 요청 DTO
 * 사용자가 대댓글을 수정할 때 전달하는 요청 데이터
 */
@Getter
@Setter
public class ReplyUpdateRequest {

    /**
     * 수정할 대댓글 내용
     */
    @NotBlank(message = "수정할 내용은 필수입니다.")
    private String content;

    /**
     * 수정 요청 사용자 ID (권한 검증용)
     */
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long writerId;
}
