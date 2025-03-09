package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User 엔티티 클래스
 * users 테이블과 매핑됨
 * 사용자 계정 정보 관리 (회원가입, 로그인, 프로필 관리)
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * 사용자 고유 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     * 각 사용자는 고유한 ID를 가짐
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 아이디 (username)
     * 로그인 시 사용되는 고유 식별자
     * 중복 불가 (unique)
     * 최대 50자 제한
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 비밀번호 (password)
     * BCrypt 해시로 암호화된 상태로 저장됨
     * 보안 강화 위해 원문 저장 금지
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 닉네임 (nickname)
     * 사용자별로 고유한 별명
     * 중복 불가 (unique)
     * 최대 100자 제한
     */
    @Column(name = "nickname", nullable = false, unique = true, length = 100)
    private String nickname;

    /**
     * 이메일 (email)
     * 로그인 및 인증용 이메일 주소
     * 중복 불가 (unique)
     * 최대 100자 제한
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 사용자 역할 (role)
     * Default: 'user'
     * 관리자(admin) 또는 일반 사용자(user)로 구분됨
     */
    @Column(name = "role", nullable = false, length = 10)
    @Builder.Default
    private String role = "user";

    /**
     * Refresh Token (JWT 재발급용)
     * 로그인 유지 및 보안 강화를 위한 JWT 토큰 저장
     * Default: null (필요 시 발급됨)
     */
    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    /**
     * 프로필 이미지 (profile_image)
     * BLOB 데이터로 저장됨 (이미지 파일)
     * Default: null (선택 사항)
     */
    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    /**
     * 계정 생성 시각 (created_at)
     * Default: 현재 시각
     * 회원가입 시 자동 설정됨
     * 수정 불가 (updateable = false)
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 계정 정보 마지막 수정 시각 (updated_at)
     * 계정 정보 변경 시 자동 갱신됨
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
