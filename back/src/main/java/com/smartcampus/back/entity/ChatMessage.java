package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ChatMessage 엔티티 클래스
 * chat_messages 테이블과 매핑됨
 * 채팅방 내 메시지를 저장하는 엔티티
 * 텍스트 및 파일(이미지, 문서 등) 전송 가능
 */
@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    /**
     * 메시지 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * 채팅방 ID (ChatRoom)
     * chat_rooms 테이블과 FK 관계 (Many-to-One)
     * 해당 메시지가 속한 채팅방 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatRoom chatRoom;

    /**
     * 보낸 사용자 ID (User)
     * users 테이블과 FK 관계 (Many-to-One)
     * 메시지를 전송한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    /**
     * 텍스트 메시지 내용
     * Not Null (필수 값)
     * 최대 2000자 제한
     */
    @Column(name = "content", columnDefinition = "CLOB", nullable = false)
    private String content;

    /**
     * 첨부 파일 (파일 데이터)
     * BLOB 형태로 저장 (이미지, 문서 등 가능)
     * 선택적 (NULL 허용)
     */
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    /**
     * 메시지 전송 시간
     * Default: 현재 시각
     * 메시지가 전송된 시간을 자동 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
