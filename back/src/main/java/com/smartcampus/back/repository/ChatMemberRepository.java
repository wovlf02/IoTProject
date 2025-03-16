package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatMember;
import com.smartcampus.back.entity.ChatRoom;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChatMemberRepository 인터페이스
 * 
 * chat_members 테이블과 매핑된 ChatMember 엔티티의 데이터 접근 계층
 * 채팅방 참여자 정보 저장, 조회, 삭제 기능 포함
 */
@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    /**
     * 특정 채팅방에 속한 모든 참여자 목록 조회
     * 
     * chat_id를 기준으로 해당 채팅방의 모든 참여자 목록을 반환
     * 
     * @param chatRoom 대상 채팅방
     * @return 특정 채팅방에 속한 참여자 목록
     */
    List<ChatMember> findByChatRoom(ChatRoom chatRoom);

    /**
     * 특정 사용자가 참여한 모든 채팅방 조회
     * 
     * user_id를 기준으로 해당 사용자가 속한 채팅방 목록을 반환
     * 
     * @param user 대상 사용자
     * @return 사용자가 참여한 채팅방 목록
     */
    List<ChatMember> findByUser(User user);

    /**
     * 특정 사용자가 특정 채팅방에 속해 있는지 확인
     * 
     * user_id와 chat_id를 기준으로 조회
     * 
     * @param user 대상 사용자
     * @param chatRoom 대상 채팅방
     * @return Optional<ChatMember> (존재하면 ChatMember 반환, 없으면 empty)
     */
    Optional<ChatMember> findByUserAndChatRoom(User user, ChatRoom chatRoom);

    /**
     * 특정 채팅방의 참여자 수 조회
     * 
     * chat_id를 기준으로 해당 채팅방의 총 참여자 수 반환
     * 
     * @param chatRoom 대상 채팅방
     * @return 특정 채팅방의 참여자 수
     */
    long countByChatRoom(ChatRoom chatRoom);

    /**
     * 특정 사용자가 특정 채팅방에서 나가도록 참여 정보 삭제
     * 
     * user_id와 chat_id를 기준으로 참여 정보 삭제
     * 
     * @param user 대상 사용자
     * @param chatRoom 대상 채팅방
     */
    void deleteByUserAndChatRoom(User user, ChatRoom chatRoom);
}
