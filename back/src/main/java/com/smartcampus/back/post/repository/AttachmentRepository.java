package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.enums.AttachmentTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 첨부파일 관련 JPA Repository
 * 다양한 타입(게시글, 댓글, 대댓글)의 첨부파일을 관리
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * 특정 대상에 연결된 모든 첨부파일 조회
     */
    List<Attachment> findByTargetIdAndTargetType(Long targetId, AttachmentTargetType targetType);

    /**
     * 대상 ID + 타입 + 첨부파일 ID로 단건 조회
     */
    Optional<Attachment> findByIdAndTargetIdAndTargetType(Long id, Long targetId, AttachmentTargetType targetType);

    /**
     * 특정 대상에 연결된 모든 첨부파일 삭제
     */
    void deleteByTargetIdAndTargetType(Long targetId, AttachmentTargetType targetType);
}
