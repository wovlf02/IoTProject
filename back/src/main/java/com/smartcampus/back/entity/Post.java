package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Clob;
import java.sql.Timestamp;

/**
 * Post 엔티티 클래스
 *
 * 사용자가 작성하는 게시글 정보를 저장하는 테이블(posts)와 매핑
 * 게시글의 제목, 본문, 작성자 정보, 생성/수정 타임스탬프 포함
 * User 엔티티와 ManyToOne 관계 (게시글 작성자)
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    /**
     * 게시글 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 게시글을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    /**
     * 작성자 ID (Foreign Key)
     *
     * User 테이블의 ID(users.id)와 연결
     * ManyToOne 관계 설정 (한 명의 사용자가 여러 개의 게시글 작성 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 성능 최적화
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_POST_USER"))
    private User user;

    /**
     * 게시글 제목
     *
     * 200자 이하, 공백 불가능
     * 제목을 기반으로 검색이 가능하도록 설정
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * 게시글 본문 (CLOB 타입)
     *
     * 길이가 긴 텍스트 데이터를 저장할 수 있도록 CLOB 타입 사용
     * 본문을 저장할 때는 HTML 태그 또는 Markdown 지원 가능
     */
    @Lob
    @Column(name = "content", nullable = false)
    private Clob content;

    /**
     * 게시글 생성 시간 (자동 설정)
     *
     * 사용자가 게시글을 저장한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     * 이후 업데이트가 되지 않음 (updatable = false)
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    /**
     * 게시글 마지막 수정 시간 (자동 업데이트)
     *
     * 사용자가 게시글을 수정할 때마다 자동으로 갱신됨
     * 데이터베이스에서 현재 시간을 업데이트
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
