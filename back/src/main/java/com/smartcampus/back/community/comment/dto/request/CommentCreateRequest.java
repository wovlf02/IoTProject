package com.smartcampus.back.community.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 댓글 또는 대댓글 생성 요청 DTO
 * <p>
 * 게시글 ID 또는 상위 댓글 ID를 기준으로 댓글을 생성할 수 있습니다.
 * 파일 첨부를 위한 multipart/form-data 요청을 지원합니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {

    @Schema(description = "댓글 본문 내용", example = "이 문제는 이렇게 푸는 게 맞을까요?")
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
}
