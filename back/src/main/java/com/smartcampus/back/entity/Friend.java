package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * Friend 엔티티 클래스
 *
 * 사용자의 친구 목록을 저장하는 테이블 (friends)과 매핑
 * 사용자(User) 간의 친구 관계를 관리
 * 친구 추가 일시 포함
 */
@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {

    /**
     * 친구 관계 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 친구 관계를 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long friendshipId;

    /**
     * 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자는 여러 명의 친구를 사귈 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FRIEND_USER"))
    private User user;

    /**
     * 친구 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자는 여러 명의 친구를 가질 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FRIEND_FRIEND"))
    private User friend;

    /**
     * 친구 추가 일시 (자동 설정)
     *
     * 사용자가 친구를 추가한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
