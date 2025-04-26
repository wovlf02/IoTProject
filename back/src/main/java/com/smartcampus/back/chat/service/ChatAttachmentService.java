package com.smartcampus.back.chat.service;

import com.smartcampus.back.chat.dto.response.ChatAttachmentResponse;
import com.smartcampus.back.chat.entity.ChatAttachment;
import com.smartcampus.back.chat.repository.ChatAttachmentRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * ChatAttachmentService
 * <p>
 * 채팅방 파일 첨부 업로드 및 다운로드를 처리하는 서비스입니다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatAttachmentService {

    private final ChatAttachmentRepository chatAttachmentRepository;

    // 로컬 저장 경로 (임시: 실제로는 S3 업로드로 대체 가능)
    private final String uploadDir = "/chat-uploads";

    /**
     * 파일 업로드 후 저장
     *
     * @param roomId 채팅방 ID
     * @param file 업로드할 파일
     * @return 업로드된 파일 정보
     */
    public ChatAttachmentResponse uploadAttachment(Long roomId, MultipartFile file) {
        try {
            // 1. 디렉토리 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. 파일 저장
            String originalFileName = file.getOriginalFilename();
            String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(savedFileName);
            file.transferTo(filePath.toFile());

            // 3. DB 저장
            ChatAttachment attachment = ChatAttachment.builder()
                    .roomId(roomId)
                    .originalFileName(originalFileName)
                    .fileUrl(filePath.toUri().toString()) // 로컬 저장이므로 URI로 반환
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            chatAttachmentRepository.save(attachment);

            // 4. 응답 변환
            return ChatAttachmentResponse.fromEntity(attachment);

        } catch (IOException e) {
            log.error("파일 업로드 실패", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 다운로드 (or 미리보기)
     *
     * @param attachmentId 첨부파일 ID
     * @return 파일 리소스
     */
    @Transactional(readOnly = true)
    public Resource downloadAttachment(Long attachmentId) {
        ChatAttachment attachment = chatAttachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

        try {
            Resource resource = new UrlResource(attachment.getFileUrl());
            if (resource.exists()) {
                return resource;
            } else {
                throw new CustomException(ErrorCode.FILE_NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            log.error("파일 URL 변환 실패", e);
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
    }
}
