package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * PostAttachment 엔티티 클래스
 * post_attachments 테이블과 매핑됨
 * 게시글(Post)과 N:1 관계 (게시글 당 여러 개의 첨부파일 가능)
 * 파일 데이터 및 파일 유형 저장
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
     * 첨부파일 ID (Primary Key)
     * 자동 증가 (IDENTITY 전략)
     * 각 첨부파일은 고유한 ID를 가짐
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    /**
     * 첨부파일이 속한 게시글 ID
     * posts 테이블과 FK 관계 (Many-to-One)
     * 게시글이 삭제되면 첨부파일도 함께 삭제됨 (CascadeType.ALL)
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * 첨부파일 데이터 (BLOB)
     * 실제 파일 데이터 저장
     * Not Null (필수 입력)
     */
    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    /**
     * 파일 유형 (MIME Type)
     * 이미지, PDF, 문서, ZIP 등 파일 유형을 저장
     * 최대 50자 제한
     * Not Null (필수 입력)
     */
    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    /**
     * 첨부파일 업로드 시작
     * Default: 현재 시각
     * 파일 업로드 시 자동 설정
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
