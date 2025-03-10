package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatMessageRepository (채팅 메시지 Repository)
 * chat_messages 테이블과 매핑됨
 * 메시지 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 특정 채팅방의 모든 메시지 조회 (최신순 정렬)
     * @param chatRoom 메시지를 조회할 채팅방 (ChatRoom 엔티티)
     * @return 해당 채팅방의 메시지 목록 (시간순 정렬)
     */
    List<ChatMessage> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);

    /**
     * 특정 사용자가 보낸 모든 메시지 조회
     * @param sender 메시지를 보낸 사용자 (User 엔티티)
     * @return 해당 사용자가 보낸 메시지 목록
     */
    List<ChatMessage> findBySender(User sender);

    /**
     * 특정 메시지를 삭제 (메시지 ID 기반)
     * @param messageId 삭제할 메시지 ID
     */
    void deleteById(Long messageId);

    /**
     * 특정 채팅방의 모든 메시지 삭제 (채팅방 삭제 시)
     * @param chatRoom 삭제할 채팅방 (ChatRoom 엔티티)
     */
    void deleteByChatRoom(ChatRoom chatRoom);
}
