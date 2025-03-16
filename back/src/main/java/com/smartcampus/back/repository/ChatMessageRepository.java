package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatMessageRepository 인터페이스
 *
 * chat_messages 테이블과 매핑된 ChatMessage 엔티티의 데이터 접근 계층
 * 채팅 메시지 저장, 조회, 삭제 기능 포함
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 특정 채팅방의 모든 메시지 조회 (최신순)
     *
     * chat_id를 기준으로 해당 채팅방의 모든 메시지를 조회
     * 최신 메시지가 가장 위에 오도록 정렬
     *
     * @param chatRoom 대상 채팅방
     * @return 특정 채팅방의 메시지 목록
     */
    List<ChatMessage> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    /**
     * 특정 사용자가 특정 채팅방에서 보낸 메시지 조회 (최신순)
     *
     * sender_id와 chat_id를 기준으로 해당 사용자가 보낸 모든 메시지를 조회
     * 최신순 정렬
     *
     * @param sender 메시지 보낸 사용자
     * @param chatRoom 대상 채팅방
     * @return 사용자가 해당 채팅방에서 보낸 메시지 목록
     */
    List<ChatMessage> findBySenderAndChatRoomOrderByCreatedAtDesc(User sender, ChatRoom chatRoom);

    /**
     * 특정 채팅방의 총 메시지 개수 조회
     *
     * chat_id를 기준으로 해당 채팅방의 메시지 개수를 반환
     *
     * @param chatRoom 대상 채팅방
     * @return 특정 채팅방의 총 메시지 개수
     */
    long countByChatRoom(ChatRoom chatRoom);

    /**
     * 특정 사용자가 특정 채팅방에서 보낸 메시지 개수 조회
     *
     * sender_id와 chat_id를 기준으로 해당 사용자가 보낸 메시지 개수를 반환
     *
     * @param sender 메시지 보낸 사용자
     * @param chatRoom 대상 채팅방
     * @return 특정 사용자가 해당 채팅방에서 보낸 메시지 개수
     */
    long countBySenderAndChatRoom(User sender, ChatRoom chatRoom);

    /**
     * 특정 채팅방의 모든 메시지 삭제
     *
     * chat_id를 기준으로 해당 채팅방의 모든 메시지를 삭제
     *
     * @param chatRoom 대상 채팅방
     */
    void deleteByChatRoom(ChatRoom chatRoom);
}
