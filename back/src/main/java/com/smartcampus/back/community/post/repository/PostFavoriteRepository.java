package com.smartcampus.back.community.post.repository;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.entity.PostFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 즐겨찾기 저장소
 * <p>
 * 사용자의 게시글 즐겨찾기 정보를 저장하고 조회하는 Repository입니다.
 * </p>
 */
@Repository
public interface PostFavoriteRepository extends JpaRepository<PostFavorite, Long> {

    /**
     * 특정 사용자와 게시글의 즐겨찾기 여부 확인
     *
     * @param user 사용자
     * @param post 게시글
     * @return 즐겨찾기 정보 (존재하면 Optional 반환)
     */
    Optional<PostFavorite> findByUserAndPost(User user, Post post);

    /**
     * 특정 사용자에 대한 즐겨찾기 게시글 전체 조회
     *
     * @param user 사용자
     * @return 즐겨찾기 목록
     */
    List<PostFavorite> findAllByUser(User user);

    /**
     * 게시글에 대한 즐겨찾기 전체 삭제 (게시글 삭제 시 함께 호출)
     *
     * @param post 게시글
     */
    void deleteAllByPost(Post post);
}
