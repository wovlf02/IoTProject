package com.smartcampus.back.chat.dto.response;

import com.smartcampus.back.chat.entity.ChatAttachment;
import lombok.Builder;
import lombok.Getter;

/**
 * ChatAttachmentResponse
 * <p>
 * 채팅 파일 업로드 완료 후 클라이언트에게 반환하는 응답 DTO입니다.
 * </p>
 */
@Getter
@Builder
public class ChatAttachmentResponse {

    /**
     * 첨부파일 고유 ID
     */
    private final Long id;

    /**
     * 원본 파일 이름
     */
    private final String originalFileName;

    /**
     * 저장된 파일 URL
     */
    private final String fileUrl;

    /**
     * 파일 타입 (MIME Type)
     */
    private final String contentType;

    /**
     * 파일 크기 (byte 단위)
     */
    private final Long fileSize;

    /**
     * ChatAttachment 엔티티를 ChatAttachmentResponse로 변환하는 메서드
     *
     * @param attachment 저장된 첨부파일 엔티티
     * @return 변환된 응답 객체
     */
    public static ChatAttachmentResponse fromEntity(ChatAttachment attachment) {
        return ChatAttachmentResponse.builder()
                .id(attachment.getId())
                .originalFileName(attachment.getOriginalFileName())
                .fileUrl(attachment.getFileUrl())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getFileSize())
                .build();
    }
}
