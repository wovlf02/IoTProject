package com.smartcampus.back.chat.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChatSessionManager
 * <p>
 * 채팅방별 WebSocket 세션(사용자) 관리를 담당하는 컴포넌트입니다.
 * 사용자의 입장/퇴장/현재 접속자 수를 관리합니다.
 * </p>
 */
@Slf4j
@Component
public class ChatSessionManager {

    /**
     * 채팅방별 접속 중인 사용자 ID 목록
     */
    private final Map<Long, Set<Long>> roomParticipants = new ConcurrentHashMap<>();

    /**
     * 사용자가 채팅방에 입장할 때 호출
     *
     * @param roomId  채팅방 ID
     * @param userId  입장하는 사용자 ID
     */
    public void enterRoom(Long roomId, Long userId) {
        roomParticipants
                .computeIfAbsent(roomId, key -> ConcurrentHashMap.newKeySet())
                .add(userId);

        log.info("User [{}] entered Room [{}]", userId, roomId);
    }

    /**
     * 사용자가 채팅방에서 퇴장할 때 호출
     *
     * @param roomId  채팅방 ID
     * @param userId  퇴장하는 사용자 ID
     */
    public void exitRoom(Long roomId, Long userId) {
        Set<Long> participants = roomParticipants.get(roomId);
        if (participants != null) {
            participants.remove(userId);
            log.info("User [{}] exited Room [{}]", userId, roomId);

            if (participants.isEmpty()) {
                roomParticipants.remove(roomId);
                log.info("Room [{}] is now empty and removed.", roomId);
            }
        }
    }

    /**
     * 특정 채팅방의 현재 접속자 수를 반환
     *
     * @param roomId 채팅방 ID
     * @return 현재 접속자 수
     */
    public int getParticipantCount(Long roomId) {
        Set<Long> participants = roomParticipants.get(roomId);
        return participants != null ? participants.size() : 0;
    }

    /**
     * 사용자가 특정 채팅방에 현재 접속 중인지 확인
     *
     * @param roomId 채팅방 ID
     * @param userId 사용자 ID
     * @return 접속 여부
     */
    public boolean isUserInRoom(Long roomId, Long userId) {
        Set<Long> participants = roomParticipants.get(roomId);
        return participants != null && participants.contains(userId);
    }
}
