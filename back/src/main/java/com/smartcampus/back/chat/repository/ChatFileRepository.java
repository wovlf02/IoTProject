package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 채팅 파일 저장소
 *
 * 메시지에 첨부된 파일의 메타데이터를 관리합니다.
 */
@Repository
public interface ChatFileRepository extends JpaRepository<ChatFile, Long> {

    /**
     * 특정 채팅 메시지 ID에 해당하는 첨부파일 조회
     *
     * @param chatMessageId 메시지 ID
     * @return Optional<ChatFile>
     */
    Optional<ChatFile> findByChatMessageId(Long chatMessageId);
}
