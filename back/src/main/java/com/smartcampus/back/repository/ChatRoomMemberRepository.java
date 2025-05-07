package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    /**
     * 채팅방에 속한 모든 멤버 목록 조회
     *
     * @param chatRoomId 채팅방 ID
     * @return 채팅방 참여자 리스트
     */
    List<ChatRoomMember> findByChatRoomId(Long chatRoomId);

    /**
     * 채팅방에 특정 사용자가 포함되어 있는지 여부 확인
     *
     * @param chatRoomId 채팅방 ID
     * @param userId 사용자 ID
     * @return 존재 여부
     */
    boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    /**
     * 채팅방 내 특정 사용자 단건 조회
     *
     * @param chatRoomId 채팅방 ID
     * @param userId 사용자 ID
     * @return 참여자 정보
     */
    Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    /**
     * 특정 사용자가 참여 중인 모든 채팅방 멤버 정보 조회
     *
     * @param userId 사용자 ID
     * @return 참여 중인 채팅방 멤버 리스트
     */
    List<ChatRoomMember> findByUserId(Long userId);
}
