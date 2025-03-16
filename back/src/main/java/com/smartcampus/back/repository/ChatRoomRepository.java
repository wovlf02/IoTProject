package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChatRoomRepository 인터페이스
 *
 * chat_rooms 테이블과 매핑된 ChatRoom 엔티티의 데이터 접근 계층
 * 채팅방 생성, 조회, 삭제 기능 포함
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 특정 사용자가 참여한 모든 채팅방 조회
     *
     * chat_members 테이블을 기준으로 특정 사용자가 포함된 채팅방 목록 반환
     *
     * @param user 대상 사용자
     * @return 사용자가 참여한 채팅방 목록
     */
    List<ChatRoom> findByMembers_User(User user);

    /**
     * 1:1 채팅방이 존재하는지 확인
     *
     * 특정 두 사용자가 참여하는 1:1 채팅방이 존재하는지 조회
     *
     * @param user1 첫 번째 사용자
     * @param user2 두 번째 사용자
     * @param isGroup 그룹 여부
     * @return Optional<ChatRoom> (존재하면 ChatRoom 반환, 없으면 empty)
     */
    Optional<ChatRoom> findByMembers_UserAndMemers_UserAndIsGroup(User user1, User user2, boolean isGroup);

    /**
     * 특정 채팅방 ID로 채팅방 조회
     *
     * ID를 기반으로 단일 채팅방을 조회
     *
     * @param chatId 채팅방 ID
     * @return Optional<ChatRoom> (존재하면 ChatRoom 반환, 없으면 empty)
     */
    Optional<ChatRoom> findById(Long chatId);

    /**
     * 특정 사용자가 참여한 그룹 채팅방 조회
     *
     * 특정 사용자가 포함된 그룹 채팅방 목록을 반환
     *
     * @param user 대상 사용자
     * @param isGroup 그룹인지 여부
     * @return 사용자가 참여한 그룹 채팅방 목록
     */
    List<ChatRoom> findByMembers_UserAndIsGroup(User user, boolean isGroup);
}
