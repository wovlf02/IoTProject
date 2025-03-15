package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * PostAttachment 엔티티 클래스
 *
 * 게시글(Post)에 첨부된 파일 정보를 저장하는 테이블 (post_attachments)과 매핑
 * 첨부파일 데이터 (파일 자체) 및 파일 유형 저장
 * 게시글(Post)과 ManyToOne 관계 설정
 */
@Entity
@Table(name = "post_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAttachment {

    /**
     * 첨부파일 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 첨부파일을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long attachmentId;

    /**
     * 게시글 ID (Foreign Key)
     *
     * Post 테이블의 post_id(posts.post_id)와 연결
     * ManyToOne 관계 설정 (한 게시글에 여러 개의 첨부파일 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ATTACHMENT_POST"))
    private Post post;

    /**
     * 첨부파일 데이터 (BLOB)
     *
     * 실제 파일 데이터 저장 (이미지, 문서 등)
     * NULL 불가능 (파일이 반드시 존재해야 함)
     */
    @Lob
    @Column(name = "file_data", nullable = false)
    private Blob fileData;

    /**
     * 파일 유형 (MIME 타입)
     *
     * 파일의 형식 저장 (예: image/png, application/pdf)
     * 최대 50자 제한
     * NULL 불가능
     */
    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    /**
     * 첨부파일 업로드 시간 (자동 설정)
     *
     * 사용자가 파일을 업로드한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
