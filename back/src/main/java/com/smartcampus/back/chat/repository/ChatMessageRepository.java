package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatMessageRepository
 * <p>
 * 채팅 메시지(ChatMessage) 관련 데이터베이스 접근을 처리하는 레포지토리입니다.
 * </p>
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 특정 채팅방(roomId)에서 삭제되지 않은 메시지 목록을 조회
     *
     * @param roomId 채팅방 고유 ID
     * @return 삭제되지 않은 메시지 리스트
     */
    List<ChatMessage> findAllByRoomIdAndDeletedFalseOrderBySentAtAsc(Long roomId);
}
