package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessage;
import com.smartcampus.back.entity.ChatReadReceipt;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChatReadReceiptRepository 인터페이스
 * 
 * chat_read_receipts 테이블과 매핑된 ChatReadReceipt 엔티티의 데이터 접근 계층
 * 메시지 읽음 상태 저장, 조회, 삭제 기능 포함
 */
@Repository
public interface ChatReadReceiptRepository extends JpaRepository<ChatReadReceipt, Long> {

    /**
     * 특정 메시지를 읽은 사용자 목록 조회
     * 
     * message_id를 기준으로 해당 메시지를 읽은 사용자 리스트 반환
     * 
     * @param chatMessage 대상 메시지
     * @return 해당 메시지를 읽은 사용자 목록
     */
    List<ChatReadReceipt> findByChatMessage(ChatMessage chatMessage);

    /**
     * 특정 사용자가 특정 메시지를 읽었는지 확인
     * 
     * user_id와 message_id를 기준으로 조회
     * 
     * @param user 읽음 확인할 사용자
     * @param chatMessage 대상 메시지
     * @return Optional<ChatReadReceipt> (존재하면 읽음 처리됨, 없으면 empty)
     */
    Optional<ChatReadReceipt> findByUserAndChatMessage(User user, ChatMessage chatMessage);

    /**
     * 특정 메시지를 아직 읽지 않은 사용자 수 조회
     * 
     * 특정 메시지를 읽지 않은 사용자 수 반환
     * 
     * @param chatMessage 대상 메시지
     * @return 읽지 않은 사용자 수
     */
    long countByChatMessage(ChatMessage chatMessage);

    /**
     * 특정 사용자가 특정 채팅방에서 읽지 않은 메시지 개수 조회
     * 
     * 사용자가 아직 읽지 않은 메시지 개수를 반환
     * 
     * @param user 대상 사용자
     * @return 해당 사용자가 아직 읽지 않은 메시지 개수
     */
    long countByUser(User user);

    /**
     * 특정 메시지의 모든 읽음 상태 삭제
     * 
     * message_id를 기준으로 해당 메시지의 모든 읽음 상태를 삭제
     * 
     * @param chatMessage 대상 메시지
     */
    void deleteByChatMessage(ChatMessage chatMessage);
}
