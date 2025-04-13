package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Attachment;
import com.smartcampus.back.post.enums.AttachmentTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 첨부파일(Attachment) 관련 JPA Repository
 * 게시글, 댓글, 대댓글 등 다양한 대상의 첨부파일 정보를 관리합니다.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * 대상 ID와 타입에 해당하는 모든 첨부파일을 조회합니다.
     *
     * @param targetId 대상 ID (게시글, 댓글, 대댓글 등)
     * @param targetType 대상 타입 (POST, COMMENT, REPLY)
     * @return 첨부파일 목록
     */
    List<Attachment> findByTargetIdAndTargetType(Long targetId, AttachmentTargetType targetType);

    /**
     * 대상 ID + 타입 + 첨부파일 ID로 단일 첨부파일을 조회합니다.
     *
     * @param id 첨부파일 ID
     * @param targetId 대상 ID
     * @param targetType 대상 타입
     * @return 첨부파일 Optional
     */
    Optional<Attachment> findByIdAndTargetIdAndTargetType(Long id, Long targetId, AttachmentTargetType targetType);

    /**
     * 특정 대상의 첨부파일을 모두 삭제합니다.
     *
     * @param targetId 대상 ID
     * @param targetType 대상 타입
     */
    void deleteByTargetIdAndTargetType(Long targetId, AttachmentTargetType targetType);
}
