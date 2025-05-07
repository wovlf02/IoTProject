package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 채팅방 ID로 메시지를 최신순으로 조회
     *
     * @param chatRoomId 채팅방 ID
     * @return 메시지 목록 (최신순 정렬)
     */
    List<ChatMessage> findByChatRoomIdOrderBySentAtDesc(Long chatRoomId);
}
