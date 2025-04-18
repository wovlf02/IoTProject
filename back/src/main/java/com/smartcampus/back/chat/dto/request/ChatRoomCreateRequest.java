package com.smartcampus.back.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 채팅방 생성 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomCreateRequest {

    @Schema(description = "채팅방 유형 (POST, STUDY, GROUP 등)", example = "POST", required = true)
    @NotBlank(message = "roomType은 필수입니다.")
    private String roomType;

    @Schema(description = "연동된 외부 ID (예: 게시글 ID, 그룹 ID 등)", example = "123", required = true)
    @NotNull(message = "refId는 필수입니다.")
    private Long refId;

    @Schema(description = "채팅방 제목", example = "자바 스터디 모임방", required = true)
    @NotBlank(message = "title은 필수입니다.")
    private String title;
}
