package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatReadReceipt;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChatReadReceiptRepository (메시지 읽음 확인 Repository)
 * chat_read_receipts 테이블과 매핑됨
 * 메시지 읽음 확인 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface ChatReadReceiptRepository extends JpaRepository<ChatReadReceipt, Long> {

    /**
     * 특정 메시지를 읽은 사용자 목록 조회
     * @param message 읽음 확인할 메시지 (ChatMessage 엔티티)
     * @return 해당 메시지를 읽은 사용자 목록
     */
    List<ChatReadReceipt> findByMessage(ChatMessage message);

    /**
     * 특정 사용자가 특정 메시지를 읽었는지 확인
     * @param message 확인할 메시지 (ChatMessage 엔티티)
     * @param user 확인할 사용자 (User 엔티티)
     * @return 읽음 정보가 존재하면 Optional 반환, 없으면 빈 Optional 반환
     */
    Optional<ChatReadReceipt> findByMessageAndUser(ChatMessage message, User user);

    /**
     * 특정 사용자의 특정 채팅방 내 읽지 않은 메시지 개수 조회
     * @param user 확인할 사용자 (User 엔티티)
     * @param chatMessages 확인할 메시지 목록 (ChatMessage 리스트
     * @return 읽지 않은 메시지 개수
     */
    long countByUserNotAndMessageIn(User user, List<ChatMessage> chatMessages);

    /**
     * 특정 채팅방 내 특정 사용자의 모든 읽음 기록 삭제 (채팅방 나갈 때)
     * @param user 삭제할 사용자 (User 엔티티)
     * @param chatMessages 삭제할 메시지 목록 (ChatMessage 리스트)
     */
    void deleteByUserAndMessageIn(User user, List<ChatMessage> chatMessages);
}
