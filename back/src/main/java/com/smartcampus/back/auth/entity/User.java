package com.smartcampus.back.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자(User) 엔티티
 *
 * 인증 및 사용자 정보 저장을 위한 기본 도메인
 * 회원가입, 로그인, 이메일 인증, 권한 관리 등에 활용
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nickname")
})
public class User {

    /**
     * 사용자 고유 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 로그인용 아이디
     */
    @Column(nullable = false, length = 50)
    private String username;

    /**
     * 로그인용 비밀번호 (BCrypt 암호화)
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 이메일
     */
    @Column(nullable = false, length = 100)
    private String email;

    /**
     * 닉네임
     */
    @Column(nullable = false, length = 30)
    private String nickname;

    /**
     * 프로필 이미지 URL 또는 경로
     */
    @Column(length = 255)
    private String profileImage;

    /**
     * FCM 토큰 (푸시 알림 전송용)
     */
    @Column(length = 300)
    private String fcmToken;

    /**
     * 사용자 권한 (ex. USER, ADMIN)
     */
    @Column(nullable = false)
    @Builder.Default
    private String role = "USER";

    /**
     * 계정 활성 상태 (탈퇴, 정지 시 false)
     */
    @Builder.Default
    private boolean active = true;

    /**
     * 회원가입 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 최정 수정 일시
     */
    private LocalDateTime updatedAt;

    /**
     * 등록 시점에 자동으로 생성일을 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 업데이트 시점에 자동으로 수정일을 설정
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
