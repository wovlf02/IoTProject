package com.smartcampus.back.chat.repository;

import com.smartcampus.back.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 채팅방 저장소
 *
 * 게시글/그룹 기반 채팅 또는 1:1 채팅방을 관리합니다.
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 특정 연동 ID와 타입 기반으로 채팅방 단건 조회
     *
     * @param refId 연동 객체 ID (게시글 ID 등)
     * @param roomType 채팅방 유형 (POST, DIRECT, STUDY 등)
     * @return Optional<ChatRoom>
     */
    Optional<ChatRoom> findByRefIdAndRoomType(Long refId, String roomType);

    /**
     * 특정 채팅방 유형의 전체 목록 조회
     *
     * @param roomType 채팅방 유형
     * @return List<ChatRoom>
     */
    List<ChatRoom> findByRoomType(String roomType);

    /**
     * 연동 ID가 없는 채팅방 조회 (ex. 1:1 Direct 채팅방에서 사용 가능)
     */
    List<ChatRoom> findByRefIdIsNullAndRoomType(String roomType);
}
