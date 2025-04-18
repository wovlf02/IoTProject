package com.smartcampus.back.community.attachment.repository;

import com.smartcampus.back.community.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 첨부파일에 대한 CRUD 및 대상별 조회 처리를 담당하는 JPA 리포지토리
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * 특정 게시글에 첨부된 파일 조회
     */
    List<Attachment> findAllByPostId(Long postId);

    /**
     * 특정 댓글에 첨부된 파일 조회
     */
    List<Attachment> findAllByCommentId(Long commentId);

    /**
     * 특정 대댓글에 첨부된 파일 조회
     */
    List<Attachment> findAllByReplyId(Long replyId);
}
