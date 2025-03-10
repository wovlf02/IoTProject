package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMember;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChatMemberRepository (채팅방 참여자 Repository)
 * chat_members 테이블과 매핑됨
 * 채팅방 참여자 정보 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    /**
     * 특정 채팅방에 참여한 모든 사용자 조회
     * @param chatRoom 조회할 채팅방 (ChatRoom 엔티티)
     * @return 해당 채팅방에 속한 모든 참여자 목록
     */
    List<ChatMember> findByChatRoom(ChatRoom chatRoom);

    /**
     * 특정 사용자가 참여 중인 모든 채팅방 조회
     * @param user 참여자 (User 엔티티)
     * @return 해당 사용자가 참여한 채팅방 목록
     */
    List<ChatMember> findByUser(User user);

    /**
     * 특정 채팅방에서 특정 사용자의 정보 조회
     * @param chatRoom 조회할 채팅방 (ChatRoom 엔티티)
     * @param user 조회할 사용자 (User 엔티티)
     * @return 해당 채팅방 내 사용자의 정보 (관리자 여부 포함)
     */
    Optional<ChatMember> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    /**
     * 특정 채팅방에서 특정 사용자의 참여 기록 삭제 (채팅방 나가기)
     * @param chatRoom 나갈 채팅방 (ChatRoom 엔티티)
     * @param user 나갈 사용자 (User 엔티티)
     */
    void deleteByChatRoomAndUser(ChatRoom chatRoom, User user);
}
