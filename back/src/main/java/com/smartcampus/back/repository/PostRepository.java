package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PostRepository (게시글 Repository)
 * posts 테이블과 매핑됨
 * 게시글 생성, 조회, 수정, 삭제 등의 기능 제공
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 특정 사용자가 작성한 게시글 목록 조회
     * @param user 게시글 작성자 (User 엔티티)
     * @return 해당 사용자가 작성한 게시글 리스트
     */
    List<Post> findByUser(User user);

    /**
     * 게시글 제목 검색 (제목에 특정 키워드 포함)
     * @param title 검색할 제목 (키워드 포함)
     * @return 제목에 해당 키워드에 포함된 게시글 목록
     */
    List<Post> findByTitleContaining(String title);

    /**
     * 특정 게시글 상세 조회 (ID 기반)
     * @param id 조회할 게시글 ID
     * @return Optional<Post> (게시글 정보)
     */
    Optional<Post> findById(Long id);

    /**
     * 특정 사용자의 게시글 개수 조회
     * @param user 게시글 작성자 (User 엔티티)
     * @return 해당 사용자가 작성한 게시글 개수
     */
    long countByUser(User user);

    /**
     * 특정 사용자의 모든 게시글 삭제
     * @param user 삭제할 게시글 작성자 (User 엔티티)
     */
    void deleteByUser(User user);
}
