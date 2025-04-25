package com.smartcampus.back.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자(User) 엔티티
 * <p>
 * SmartCampus 앱의 회원가입, 로그인, 커뮤니티 활동 등을 위한 사용자 기본 정보를 관리합니다.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // 테이블 이름을 users로 설정
public class User {

    /**
     * 사용자 고유 ID (PK)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 로그인 아이디 (username)
     */
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    /**
     * 암호화된 비밀번호
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 닉네임
     */
    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    /**
     * 사용자 이메일 주소
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 프로필 이미지 URL (nullable)
     */
    private String profileImageUrl;

    /**
     * 계정 상태 (ACTIVE, SUSPENDED, WITHDRAWN)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /**
     * 사용자 권한 (USER, ADMIN)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * 가입 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 최종 수정 일시
     */
    private LocalDateTime updatedAt;

    // -------------------------------------------------

    /**
     * 최초 저장 시 자동으로 createdAt 설정
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.role = Role.USER;
    }

    /**
     * 수정 시 updatedAt 자동 갱신
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // -------------------------------------------------

    /**
     * 사용자 계정 상태 ENUM
     */
    public enum Status {
        ACTIVE,      // 정상
        SUSPENDED,   // 정지
        WITHDRAWN    // 탈퇴
    }

    /**
     * 사용자 권한 ENUM
     */
    public enum Role {
        USER, ADMIN
    }
}
