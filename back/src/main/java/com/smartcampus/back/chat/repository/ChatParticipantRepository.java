package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatParticipant;
import com.smartcampus.back.chat.entity.ChatRoom;
import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 채팅방 참가자 저장소
 *
 * 사용자의 채팅방 참여 상태 및 기록을 관리합니다.
 */
@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    /**
     * 특정 채팅방 + 사용자 기준 단건 조회
     *
     * @param chatRoom 채팅방
     * @param user 사용자
     * @return Optional<ChatParticipant>
     */
    Optional<ChatParticipant> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    /**
     * 사용자 기준으로 참여 중인 채팅방 목록 조회
     *
     * @param user 사용자
     * @param active 참여 중 상태
     * @return List<ChatParticipant>
     */
    List<ChatParticipant> findByUserAndActive(User user, boolean active);

    /**
     * 채팅방 기준 현재 참여자 목록 조회
     *
     * @param chatRoom 채팅방
     * @param active 참여 상태
     * @return List<ChatParticipant>
     */
    List<ChatParticipant> findByChatRoomAndActive(ChatRoom chatRoom, boolean active);

    /**
     * 채팅방 ID + 사용자 ID로 참가 여부 조회
     */
    boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
