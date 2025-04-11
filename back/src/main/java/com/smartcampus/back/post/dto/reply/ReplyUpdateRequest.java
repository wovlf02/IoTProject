package com.smartcampus.back.post.dto.reply;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 대댓글(답글) 수정 요청 DTO
 * 기존에 작성된 답글을 수정할 때 사용됨
 */
@Getter
@Setter
public class ReplyUpdateRequest {

    /**
     * 수정할 대댓글 내용
     */
    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    private String content;
}
