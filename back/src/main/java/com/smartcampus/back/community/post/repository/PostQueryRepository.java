package com.smartcampus.back.community.post.repository;

import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.entity.PostCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 커스텀 조회 전용 Repository
 * <p>
 * 인기글, 키워드 검색, 조건별 정렬 등 커스텀 쿼리를 담당합니다.
 */
@Repository
public interface PostQueryRepository extends Repository<Post, Long> {

    /**
     * 전체 게시글 목록 조회 (카테고리 + 페이지네이션)
     *
     * @param category 카테고리
     * @param pageable 페이징 정보
     * @return 게시글 목록
     */
    @Query("SELECT p FROM Post p WHERE p.category = :category AND p.deleted = false ORDER BY p.createdAt DESC")
    List<Post> findAllByCategory(@Param("category") PostCategory category, Pageable pageable);

    /**
     * 인기글 조회 (좋아요 수 + 조회수 기준 상위 n개)
     *
     * @param pageable 페이징 정보
     * @return 인기 게시글 목록
     */
    @Query("SELECT p FROM Post p WHERE p.deleted = false ORDER BY (p.likeCount * 2 + p.viewCount) DESC")
    List<Post> findPopularPosts(Pageable pageable);

    /**
     * 키워드 검색 (제목 + 내용)
     *
     * @param keyword  검색어
     * @param pageable 페이징 정보
     * @return 검색 결과
     */
    @Query("SELECT p FROM Post p WHERE p.deleted = false AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Post> searchPostsByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 즐겨찾기한 게시글 조회
     *
     * @param userId   사용자 ID
     * @param pageable 페이징 정보
     * @return 즐겨찾기 게시글
     */
    @Query("SELECT pf.post FROM PostFavorite pf WHERE pf.user.id = :userId")
    List<Post> findFavoritePostsByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * 작성자 기준 게시글 조회
     *
     * @param userId   사용자 ID
     * @param pageable 페이징 정보
     * @return 해당 사용자가 작성한 게시글
     */
    @Query("SELECT p FROM Post p WHERE p.writer.id = :userId AND p.deleted = false")
    List<Post> findPostsByWriter(@Param("userId") Long userId, Pageable pageable);
}
