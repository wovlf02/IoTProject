package com.smartcampus.back.chat.service;

import com.smartcampus.back.chat.entity.ChatFile;
import com.smartcampus.back.chat.entity.ChatMessage;
import com.smartcampus.back.chat.repository.ChatFileRepository;
import com.smartcampus.back.chat.repository.ChatMessageRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 채팅 메시지 첨부파일 처리 서비스
 *
 * 파일 업로드, 다운로드, 미리보기 등을 지원합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatAttachmentService {

    private final ChatFileRepository chatFileRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final String baseUploadDir = "/upload/chat"; // 실제 경로로 수정 필요

    /**
     * 채팅 메시지에 첨부파일 저장
     */
    public ChatFile storeChatFile(Long messageId, MultipartFile file) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        String originalName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalName);
        String storedName = System.currentTimeMillis() + "_" + originalName;
        String storedPath = baseUploadDir + "/" + storedName;

        try {
            File dest = new File(storedPath);
            file.transferTo(dest);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }

        ChatFile chatFile = ChatFile.builder()
                .originalName(originalName)
                .storedPath(storedPath)
                .contentType(file.getContentType())
                .size(file.getSize())
                .chatMessage(message)
                .build();

        return chatFileRepository.save(chatFile);
    }

    /**
     * 채팅 파일 다운로드 처리
     */
    public void downloadFile(Long messageId, HttpServletResponse response) {
        ChatFile chatFile = chatFileRepository.findByChatMessageId(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        try {
            File file = new File(chatFile.getStoredPath());

            if (!file.exists()) {
                throw new CustomException(ErrorCode.NOT_FOUND);
            }

            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + URLEncoder.encode(chatFile.getOriginalName(), UTF_8) + "\"");

            try (InputStream is = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                os.flush();
            }

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }
    }

    /**
     * 채팅 이미지 파일 미리보기 스트림 반환
     */
    public FileSystemResource previewImage(Long fileId) {
        ChatFile file = chatFileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Path path = Paths.get(file.getStoredPath());
        if (!Files.exists(path) || !file.getContentType().startsWith("image")) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        return new FileSystemResource(path.toFile());
    }
}
