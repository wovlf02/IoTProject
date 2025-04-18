package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 채팅 메시지 저장소
 *
 * 채팅방 내 메시지 저장 및 조회 기능을 담당합니다.
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 채팅방 ID 기준으로 메시지 목록을 페이징 조회
     *
     * @param chatRoomId 채팅방 ID
     * @param pageable 페이지 정보
     * @return Page<ChatMessage>
     */
    Page<ChatMessage> findByChatRoomIdOrderBySentAtDesc(Long chatRoomId, Pageable pageable);

    /**
     * 특정 채팅방의 모든 메시지를 시간 오름차순으로 조회 (옵션)
     *
     * @param chatRoomId 채팅방 ID
     * @return List<ChatMessage>
     */
    List<ChatMessage> findByChatRoomIdOrderBySentAtAsc(Long chatRoomId);
}
