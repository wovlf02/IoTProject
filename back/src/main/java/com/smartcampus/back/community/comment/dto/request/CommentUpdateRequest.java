package com.smartcampus.back.community.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 댓글 또는 대댓글 수정 요청 DTO
 * <p>
 * 기존 댓글의 내용을 수정하거나 첨부파일을 교체할 때 사용됩니다.
 * multipart/form-data 요청으로 사용되며, 파일은 별도로 @RequestParam으로 전달됩니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequest {

    @Schema(description = "수정할 댓글 내용", example = "이 부분을 이렇게 바꿔보는 건 어떤가요?")
    @NotBlank(message = "댓글 내용은 공백일 수 없습니다.")
    private String content;
}
