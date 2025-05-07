package com.smartcampus.back.repository;

import com.smartcampus.back.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 필요 시 사용자 기반 조회 메서드 등 추가 가능
}
