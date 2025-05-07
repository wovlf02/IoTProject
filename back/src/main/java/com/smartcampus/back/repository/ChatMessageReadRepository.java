package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMessageReadRepository extends JpaRepository<ChatMessageRead, Long> {

    /**
     * 특정 메시지를 특정 사용자가 읽었는지 여부 조회
     *
     * @param chatMessageId 메시지 ID
     * @param userId 사용자 ID
     * @return 읽음 상태 정보
     */
    Optional<ChatMessageRead> findByChatMessageIdAndUserId(Long chatMessageId, Long userId);

    /**
     * 특정 채팅방에서 특정 사용자가 읽지 않은 메시지 수 조회
     * → 해당 채팅방의 메시지 중 해당 사용자가 아직 읽지 않은 메시지 수
     *
     * @param chatRoomId 채팅방 ID
     * @param userId 사용자 ID
     * @return 안 읽은 메시지 수
     */
    long countByChatMessage_ChatRoom_IdAndUserIdAndReadAtIsNull(Long chatRoomId, Long userId);

    /**
     * 특정 메시지를 읽은 사용자 수 조회
     * → 카카오톡처럼 마지막 메시지를 기준으로 읽은 사람 수 파악
     *
     * @param chatMessageId 메시지 ID
     * @return 읽은 사용자 수
     */
    long countByChatMessageId(Long chatMessageId);
}
