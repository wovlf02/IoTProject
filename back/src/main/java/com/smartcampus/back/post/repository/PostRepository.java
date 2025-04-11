package com.smartcampus.back.post.repository;

import com.smartcampus.back.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시글(Post) 관련 JPA Repository
 * 게시글의 기본 CRUD, 페이징, 검색, 필터링 기능 제공
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 게시글 제목 또는 내용에 키워드가 포함된 게시글 페이징 조회
     *
     * @param keyword 검색 키워드 (제목 또는 본문에 포함)
     * @param pageable 페이징 정보 (페이지 번호, 크기 등)
     * @return 검색된 게시글 페이지
     */
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String keyword,
            String keywordAgain,
            Pageable pageable
    );

    /**
     * 특정 작성자의 게시글 페이징 조회
     *
     * @param writerId 작성자 ID
     * @param pageable 페이징 정보
     * @return 게시글 페이지
     */
    Page<Post> findByWriterId(Long writerId, Pageable pageable);

    /**
     * 공개 여부가 true인 게시글만 페이징하여 조회
     * 공개 여부가 false인 비공개 게시글은 포함되지 않음
     *
     * @param pageable 페이징 정보를 담은 객체 (페이지 번호, 크기 등)
     * @return 공개 게시글 목록의 페이지 객체
     */
    Page<Post> findByIsPublicTrue(Pageable pageable);
}
