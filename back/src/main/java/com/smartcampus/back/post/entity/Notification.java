package com.smartcampus.back.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자에게 전송된 알림 정보를 저장하는 엔티티입니다.
 * 알림 제목, 본문, 수신자, 생성 시각, 읽음 여부 등의 정보를 포함합니다.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    /**
     * 알림 고유 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 알림을 수신한 사용자 ID
     */
    private Long userId;

    /**
     * 알림 제목
     */
    private String title;

    /**
     * 알림 본문 내용
     */
    private String body;

    /**
     * 알림 읽음 여부 (기본값 false)
     */
    private boolean read = false;

    /**
     * 알림 생성 시각 (자동 저장)
     */
    private LocalDateTime createdAt;
}
