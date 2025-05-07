package com.smartcampus.back.dto.chat.response;

import com.smartcampus.back.entity.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 채팅방 참여자 정보를 담는 응답 DTO입니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class ChatMemberResponse {

    /** 사용자 ID */
    private Long userId;

    /** 사용자 닉네임 */
    private String nickname;

    /** 프로필 이미지 URL */
    private String profileImageUrl;

    /** 채팅방 내 역할 (ADMIN / MEMBER) */
    private String role;

    /**
     * ChatRoomMember 엔티티를 기반으로 DTO 객체 생성
     *
     * @param member ChatRoomMember 엔티티
     * @return ChatMemberResponse DTO
     */
    public static ChatMemberResponse from(ChatRoomMember member) {
        return ChatMemberResponse.builder()
                .userId(member.getUser().getId())
                .nickname(member.getUser().getNickname())
                .profileImageUrl(member.getUser().getProfileImageUrl())
                .role(member.getRole())
                .build();
    }
}
