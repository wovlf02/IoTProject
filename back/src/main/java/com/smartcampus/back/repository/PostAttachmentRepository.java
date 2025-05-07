package com.smartcampus.back.repository;

import com.smartcampus.back.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Long> {

    /**
     * 특정 게시글에 첨부된 파일 목록 조회
     *
     * @param postId 게시글 ID
     * @return 첨부파일 리스트
     */
    List<PostAttachment> findByPostId(Long postId);
}
