package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatRoomRepositor (채팅방 Repository)
 * chat_rooms 테이블과 매핑됨
 * 채팅방 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 특정 사용자가 참여 중인 채팅방 목록 조회
     * @param user 채팅방에 참여한 사용자 (User 엔티티)
     * @return 해당 사용자가 참여한 채팅방 목록
     */
    List<ChatRoom> findByMember(User user);

    /**
     * 특정 이름을 가진 채팅방 조회
     * @param roomName 채팅방 이름
     * @return 해당 이름을 가진 채팅방 목록
     */
    List<ChatRoom> findByRoomNameContaining(String roomName);

    /**
     * 단체 채팅방인지 여부를 기준으로 채팅방 목록 조회
     * @param isGroup 단체 채팅 여부 (true: 단체 채팅, false: 1:1 채팅)
     * @return 해당 조건에 맞는 채팅방 목록
     */
    List<ChatRoom> findByIsGroup(boolean isGroup);
}
