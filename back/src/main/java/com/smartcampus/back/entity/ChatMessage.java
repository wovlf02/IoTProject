package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * ChatMessage 엔티티 클래스
 *
 * 특정 채팅방(ChatRoom)에서 주고받은 메시지를 저장하는 테이블 (chat_messages)과 매핑
 * 메시지 전송자(User)와 채팅방(ChatRoom) 간의 관계 설정
 * 텍스트 메시지 및 첨부파일 데이터 저장
 * 메시지 전송 시각 포함
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
     * 메시지 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 메시지를 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 채팅방 ID (Foreign Key)
     *
     * ChatRoom 테이블의 chat_id(chat_rooms.chat_id)와 연결
     * ManyToOne 관계 설정 (한 채팅방에 여러 개의 메시지가 존재 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MESSAGE_CHAT"))
    private ChatRoom chatRoom;

    /**
     * 메시지 전송자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 개의 메시지를 전송 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MESSAGE_SENDER"))
    private User sender;

    /**
     * 텍스트 메시지 내용
     *
     * 최대 1000자까지 저장 가능
     * 공백 가능 (파일만 전송하는 경우를 대비)
     */
    @Column(name = "content", length = 1000)
    private String content;

    /**
     * 첨부 파일 데이터 (BLOB)
     *
     * 메시지에 포함된 파일 데이터 (이미지, 문서 등)
     * NULL 가능 (텍스트 메시지만 전송할 수도 있음)
     */
    @Lob
    @Column(name = "file_data")
    private Blob fileData;

    /**
     * 메시지 전송 시간 (자동 설정)
     *
     * 사용가 메시지를 전송한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
