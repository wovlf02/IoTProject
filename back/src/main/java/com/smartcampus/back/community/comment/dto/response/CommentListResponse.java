package com.smartcampus.back.community.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 게시글 기준 전체 댓글 및 대댓글 목록 응답 DTO
 * <p>
 * 계층형 구조로 댓글을 표현하며, 각 댓글은 대댓글을 포함할 수 있습니다.
 * 프론트는 이 구조를 그대로 트리 또는 인덴트 방식으로 렌더링하면 됩니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListResponse {

    @Schema(description = "댓글 목록 (계층형 구조)")
    private List<CommentResponse> comments;
}
