package com.smartcampus.back.community.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 댓글/대댓글 단일 조회 응답 DTO
 * <p>
 * 작성자 정보, 작성 시간, 좋아요 수, 자식 댓글 목록 등을 포함합니다.
 * 댓글 또는 대댓글을 계층형 구조로 반환할 수 있도록 children 리스트를 포함합니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 또는 대댓글 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "101")
    private Long commentId;

    @Schema(description = "댓글 작성자 닉네임", example = "sunnyCoder")
    private String nickname;

    @Schema(description = "댓글 작성자 프로필 이미지 URL", example = "https://cdn.example.com/images/profile123.jpg")
    private String profileImageUrl;

    @Schema(description = "댓글 본문 내용", example = "이 문제는 이런 방식으로도 풀 수 있습니다.")
    private String content;

    @Schema(description = "댓글 작성 시간", example = "2024-04-18T14:32:00")
    private LocalDateTime createdAt;

    @Schema(description = "댓글 수정 시간", example = "2024-04-18T15:02:00")
    private LocalDateTime updatedAt;

    @Schema(description = "좋아요 수", example = "7")
    private int likeCount;

    @Schema(description = "현재 로그인 사용자가 좋아요를 눌렀는지 여부", example = "true")
    private boolean liked;

    @Schema(description = "해당 댓글이 차단되었는지 여부", example = "false")
    private boolean blocked;

    @Schema(description = "삭제된 댓글 여부", example = "false")
    private boolean deleted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "대댓글 리스트 (없을 경우 null 또는 빈 배열)", nullable = true)
    private List<CommentResponse> children;
}
