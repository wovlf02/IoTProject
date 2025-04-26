package com.smartcampus.back.chat.dto.request;

import com.smartcampus.back.chat.entity.ChatMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ChatMessageRequest
 * <p>
 * 채팅방에 메시지를 전송할 때 사용하는 요청 DTO입니다.
 * (텍스트 메시지, 파일 메시지 구분 지원)
 * </p>
 */
@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    /**
     * 메시지 타입 (TEXT, FILE)
     */
    @NotNull(message = "메시지 타입을 입력해 주세요.")
    private ChatMessage.MessageType messageType;

    /**
     * 메시지 텍스트 (텍스트 타입일 경우)
     */
    private String content;

    /**
     * 첨부파일 ID (파일 타입일 경우)
     */
    private Long attachmentId;
}
