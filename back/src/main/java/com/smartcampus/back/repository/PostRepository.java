package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PostRepository 인터페이스
 *
 * posts 테이블과 매핑된 Post 엔티티의 데이터 접근 계층
 * 게시글 저장, 조회, 수정, 삭제 기능 포함
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 특정 사용자가 작성한 게시글 목록 조회
     *
     * user_id를 기준으로 해당 사용자의 게시글 리스트를 반환
     *
     * @param user 게시글 작성자 (User 객체)
     * @return 사용자가 작성한 게시글 목록
     */
    List<Post> findByUser(User user);

    /**
     * 특정 게시글 ID로 게시글 조회
     *
     * ID를 기반으로 단일 게시글을 조회
     * 결과가 없을 경우 Optional.empty() 반환
     *
     * @param postId 게시글 ID
     * @return Optional<Post> 객체 (게시글이 존재하면 Post, 없으면 empty)
     */
    Optional<Post> findById(Long postId);

    /**
     * 키워드를 포함하는 게시글 검색
     *
     * 제목(title) 또는 본문(content)에 특정 키워드가 포함된 게시글 조회
     * 검색어는 대소문자를 구분하지 않도록 설정 (LOWER)
     *
     * @param keyword 검색할 키워드
     * @return 키워드가 포함된 게시글 목록
     */
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 최신 게시글 목록 조회 (최대 n개)
     *
     * 게시글을 생성일(createdAt) 기준 내림차순 정렬하여 최신 순으로 가져옴
     * n개의 게시글을 가져오도록 설정
     *
     * @param limit 가져올 게시글 개수
     * @return 최신 게시글 목록
     */
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findLatestPosts(@Param("limit") int limit);
}
