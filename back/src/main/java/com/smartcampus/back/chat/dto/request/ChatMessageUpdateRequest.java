package com.smartcampus.back.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatMessageUpdateRequest
 * <p>
 * 채팅 메시지를 수정할 때 사용하는 요청 DTO입니다.
 * (텍스트 메시지 내용만 수정할 수 있습니다.)
 * </p>
 */
@Getter
@NoArgsConstructor
public class ChatMessageUpdateRequest {

    /**
     * 수정할 메시지 내용
     */
    @NotBlank(message = "수정할 메시지 내용을 입력해 주세요.")
    private String content;
}
