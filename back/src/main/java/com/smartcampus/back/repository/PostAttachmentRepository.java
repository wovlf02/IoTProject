package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PostAttachmentRepository (게시글 첨부파일 Repository)
 * post_attachments 테이블과 매핑됨
 * 첨부파일 저장, 조회, 삭제 기능 제공
 */
@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Long> {

    /**
     * 특정 게시글에 속한 첨부파일 목록 조회
     * @param post 첨부파일이 속한 게시글 (Post 엔티티)
     * @return 해당 게시글에 첨부된 파일 목록
     */
    List<PostAttachment> findByPost(Post post);

    /**
     * 특정 첨부파일 ID로 조회
     * @param id 첨부파일 ID
     * @return 첨부파일 정보
     */
    PostAttachment findById(long id);

    /**
     * 특정 게시글의 모든 첨부파일 삭제
     * @param post 삭제할 게시글 (Post 엔티티)
     */
    void deleteByPost(Post post);
}
