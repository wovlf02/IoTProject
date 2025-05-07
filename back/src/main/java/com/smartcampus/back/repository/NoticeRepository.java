package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    /**
     * 공지사항 전체를 최신순으로 조회
     *
     * @return 등록일 기준 내림차순 정렬된 공지사항 리스트
     */
    List<Notice> findAllByOrderByCreatedAtDesc();
}
