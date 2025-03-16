package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PostAttachmentRepository 인터페이스
 *
 * post_attachments 테이블과 매핑된 PostAttachment 엔티티의 데이터 접근 계층
 * 게시글 첨부파일 저장, 조회, 삭제 기능 포함
 */
@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Long> {

    /**
     * 특정 게시글에 속한 모든 첨부파일 조회
     *
     * post_id를 기준으로 해당 게시글의 모든 첨부파일 리스트를 반환
     *
     * @param post 대상 게시글
     * @return 특정 게시글에 첨부된 파일 목록
     */
    List<PostAttachment> findByPost(Post post);

    /**
     * 특정 게시글의 모든 첨부파일 삭제
     * 
     * post_id를 기준으로 해당 게시글의 첨부파일을 모두 삭제
     * 
     * @param post 대상 게시글
     */
    void deleteByPost(Post post);
}
