package com.smartcampus.back.community.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 댓글 생성, 수정, 삭제 요청에 대한 간단한 응답 DTO
 * <p>
 * commentId와 처리 메시지만 반환하며, 상태 확인용으로 활용됩니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 요청 처리에 대한 단순 응답")
public class CommentSimpleResponse {

    @Schema(description = "댓글 ID", example = "123")
    private Long commentId;

    @Schema(description = "처리 메시지", example = "댓글이 등록되었습니다.")
    private String message;
}
