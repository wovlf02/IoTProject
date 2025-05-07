package com.smartcampus.back.repository;

import com.smartcampus.back.entity.NoticeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachment, Long> {

    /**
     * 특정 공지사항에 첨부된 파일 목록 조회
     *
     * @param noticeId 공지사항 ID
     * @return 첨부파일 리스트
     */
    List<NoticeAttachment> findByNoticeId(Long noticeId);
}
