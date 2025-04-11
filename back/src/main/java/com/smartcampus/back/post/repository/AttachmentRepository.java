package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 첨부파일 관련 JPA Repository
 * 게시글에 연결된 첨부파일을 조회하거나 삭제하는데 사용됨
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    /**
     * 특정 게시글의 첨부파일 전체 조회
     *
     * @param postId 게시글 ID
     * @return 첨부파일 리스트
     */
    List<Attachment> findByPostId(Long postId);

    /**
     * 게시글 ID와 첨부파일 ID로 특정 첨부파일을 조회
     *
     * @param id 첨부파일 ID
     * @param postId 게시글 ID
     * @return 첨부파일 (Optional)
     */
    Attachment findByIdAndPostId(Long id, Long postId);

    /**
     * 게시글 ID를 기준으로 첨부파일 전체 삭제
     *
     * @param postId 게시글 ID
     */
    void deleteByPostId(Long postId);
}
