package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User 엔티티 클래스
 *
 * 사용자 정보를 저장하는 데이터베이스 테이블(users)과 매핑되는 클래스
 * 기본적인 사용자 정보 (아이디, 비밀번호, 이메일, 닉네임) 포함
 * 보안 관련 정보 (비밀번호 암호화, Refresh Token) 포함
 * 프로필 이미지(Blob), 계정 생성 및 수정 타임스탬프 포함
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USERNAME", columnNames = "username"), // username 중복 방지
        @UniqueConstraint(name = "UK_EMAIL", columnNames = "email") // email 중복 방지
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * 사용자 고유 ID (Primary Key)
     *
     * 자동 증가 전략 사용 (IDENTITY)
     * 각 사용자별 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 사용자 아이디 (Unique)
     *
     * 로그인 시 사용되는 고유 식별자
     * 50자 이하, 공백 불가능
     * 데이터베이스에서 UNIQUE 제약 조건 적용
     */
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    /**
     * 사용자 비밀번호 (암호화 저장)
     *
     * BCrypt 알고리즘을 사용하여 암호화된 상태로 저장
     * 255자 이하, 공백 불가능
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * 사용자 닉네임 (Unique)
     *
     * UI에서 표시되는 사용자 이름
     * 100자 이하, 공백 불가능
     * 데이터베이스에서 UNIQUE 제약 조건 적용
     */
    @Column(name = "nickname", nullable = false, unique = true, length = 100)
    private String nickname;

    /**
     * 사용자 이메일 주소 (Unique)
     *
     * 이메일 인증 및 계정 복구 시 사용
     * 100자 이하, 공백 불가능
     * 데이터베이스에서 UNIQUE 제약 조건 적용
     */
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    /**
     * 사용자 역할 (Role)
     *
     * Default: "user"
     * 관리자(admin) 또는 일반 사용자(user)로 설정 가능
     */
    @Column(name = "role", nullable = false, length = 10)
    @Builder.Default
    private String role = "user";

    /**
     * Refresh Token (JWT 기반 인증에서 사용)
     *
     * JWT 토큰 갱신을 위해 사용
     * BCrypt를 사용하여 암호화된 형태로 저장됨
     * 최대 500자 저장 가능
     * Default: null
     */
    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    /**
     * 프로필 이미지 (Blob 데이터)
     *
     * 사용자 아바타 또는 사진 저장
     * NULL 가능 (기본 프로필 이미지가 설정될 수 있음)
     */
    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    /**
     * 계정 생성 시간 (자동 설정)
     *
     * 사용자가 계정을 생성한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     * 이후 업데이트되지 않음 (updatable = false)
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * 마지막 수정 시간 (자동 업데이트)
     *
     * 사용자의 정보가 수정될 때마다 자동으로 갱신됨
     * 데이터베이스에서 현재 시간을 업데이트
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
