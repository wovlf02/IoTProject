package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatRoomRepository
 * <p>
 * 채팅방(ChatRoom) 관련 데이터베이스 접근을 처리하는 레포지토리입니다.
 * </p>
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 채팅방 타입별로 조회
     *
     * @param roomType 채팅방 타입 (DIRECT, GROUP)
     * @return 해당 타입의 채팅방 리스트
     */
    List<ChatRoom> findAllByRoomType(ChatRoom.RoomType roomType);
}
