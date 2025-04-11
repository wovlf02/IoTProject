package com.smartcampus.back.post.dto.reply;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 대댓글(답글) 생성 요청 DTO
 * 댓글에 대한 답글을 작성할 때 클라이언트가 전달하는 요청 데이터
 */
@Getter
@Setter
public class ReplyCreateRequest {

    /**
     * 대댓글 내용
     */
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    /**
     * 대댓글 작성자 ID
     */
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long writerId;
}
