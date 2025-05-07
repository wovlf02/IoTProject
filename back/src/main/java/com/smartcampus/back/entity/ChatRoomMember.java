package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_room_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_member_seq_gen")
    @SequenceGenerator(name = "chat_member_seq_gen", sequenceName = "chat_member_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "role", nullable = false, length = 20)
    private String role; // MEMBER / ADMIN 등

    /**
     * ChatRoomMember 객체를 정적으로 생성하는 팩토리 메서드입니다.
     *
     * @param chatRoom 소속 채팅방
     * @param user 사용자
     * @param role 역할 (예: MEMBER, ADMIN)
     * @return 생성된 ChatRoomMember 객체
     */
    public static ChatRoomMember of(ChatRoom chatRoom, User user, String role) {
        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .user(user)
                .role(role)
                .build();
    }
}
