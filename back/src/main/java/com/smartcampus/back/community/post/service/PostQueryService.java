package com.smartcampus.back.community.post.service;

import com.smartcampus.back.community.post.dto.response.*;
import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.repository.PostQueryRepository;
import com.smartcampus.back.community.post.repository.PostRepository;
import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 목록, 검색, 인기글 등 조회 전용 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    /**
     * 게시글 전체 목록 조회 (페이징)
     *
     * @param pageable 페이지 정보
     * @return 게시글 목록 응답
     */
    public PostListResponse getPostList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostResponse> content = posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        return PostListResponse.of(content, posts.getTotalElements(), posts.getTotalPages());
    }

    /**
     * 게시글 키워드 검색
     *
     * @param keyword 검색어
     * @param pageable 페이지 정보
     * @return 검색 결과 게시글 목록 응답
     */
    public PostListResponse searchPosts(String keyword, Pageable pageable) {
        Page<Post> posts = postQueryRepository.searchByKeyword(keyword, pageable);

        List<PostResponse> content = posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        return PostListResponse.of(content, posts.getTotalElements(), posts.getTotalPages());
    }

    /**
     * 인기 게시글 조회 (기준: 좋아요 수, 조회수 등)
     *
     * @param pageable 페이지 정보
     * @return 인기 게시글 응답
     */
    public PopularPostListResponse getPopularPosts(Pageable pageable) {
        List<Post> popularPosts = postQueryRepository.findPopularPosts(pageable);

        List<PostResponse> content = popularPosts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        return new PopularPostListResponse(content);
    }

    /**
     * 즐겨찾기한 게시글 목록 조회
     *
     * @param user 현재 사용자
     * @return 즐겨찾기한 게시글 목록 응답
     */
    public FavoritePostListResponse getFavoritePosts(User user) {
        List<Post> favorites = postQueryRepository.findFavoritePostsByUser(user);

        List<PostResponse> content = favorites.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        return new FavoritePostListResponse(content);
    }

    /**
     * 게시글 상세 조회
     *
     * @param postId 게시글 ID
     * @return 게시글 상세 정보
     */
    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        return PostResponse.from(post);
    }

    /**
     * 사용자 활동 순위 (게시글/댓글 수 기반)
     *
     * @return 랭킹 응답
     */
    public RankingResponse getUserActivityRanking() {
        return postQueryRepository.aggregateRanking();
    }
}
