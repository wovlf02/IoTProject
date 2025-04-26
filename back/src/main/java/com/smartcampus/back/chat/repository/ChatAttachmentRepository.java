package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ChatAttachmentRepository
 * <p>
 * 채팅 첨부파일(ChatAttachment) 관련 데이터베이스 접근을 처리하는 레포지토리입니다.
 * </p>
 */
@Repository
public interface ChatAttachmentRepository extends JpaRepository<ChatAttachment, Long> {

    /**
     * 첨부파일 ID로 첨부파일 조회
     *
     * @param id 첨부파일 고유 ID
     * @return 조회된 ChatAttachment (Optional)
     */
    Optional<ChatAttachment> findById(Long id);
}
