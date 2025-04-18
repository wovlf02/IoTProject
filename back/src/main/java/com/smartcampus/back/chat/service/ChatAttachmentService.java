package com.smartcampus.back.chat.service;

import com.smartcampus.back.chat.entity.ChatFile;
import com.smartcampus.back.chat.entity.ChatMessage;
import com.smartcampus.back.chat.repository.ChatFileRepository;
import com.smartcampus.back.chat.repository.ChatMessageRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 채팅 메시지 첨부파일 처리 서비스
 * <p>
 * 파일 업로드, 다운로드, 미리보기 등을 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatAttachmentService {

    private final ChatFileRepository chatFileRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 실제 운영 시 application.yml 또는 환경변수로 외부 설정 권장
    private final String baseUploadDir = "uploads/chat";  // 루트 기준 상대 경로

    /**
     * 채팅 메시지에 첨부파일 저장
     *
     * @param file MultipartFile
     * @param messageId 연결된 채팅 메시지 ID
     */
    public void storeFile(MultipartFile file, Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(originalName);
        String storedName = System.currentTimeMillis() + "_" + originalName;
        String storedPath = baseUploadDir + File.separator + storedName;

        try {
            // 디렉터리 존재하지 않으면 생성
            File uploadDir = new File(baseUploadDir);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File dest = new File(storedPath);
            file.transferTo(dest);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        ChatFile chatFile = ChatFile.builder()
                .originalName(originalName)
                .storedPath(storedPath)
                .contentType(file.getContentType())
                .size(file.getSize())
                .chatMessage(message)
                .build();

        chatFileRepository.save(chatFile);
    }

    /**
     * 첨부파일 다운로드 리소스 반환
     *
     * @param messageId 첨부파일이 연결된 메시지 ID
     * @return Resource for 다운로드
     */
    public Resource loadFileAsResource(Long messageId) {
        ChatFile chatFile = chatFileRepository.findByChatMessageId(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        Path path = Paths.get(chatFile.getStoredPath());
        if (!Files.exists(path)) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

        return new FileSystemResource(path);
    }

    /**
     * 이미지 미리보기용 리소스 반환
     *
     * @param fileId ChatFile ID
     * @return 이미지 Resource
     */
    public Resource loadImageAsResource(Long fileId) {
        ChatFile file = chatFileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        Path path = Paths.get(file.getStoredPath());
        if (!Files.exists(path) || file.getContentType() == null || !file.getContentType().startsWith("image")) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

        return new FileSystemResource(path);
    }
}
