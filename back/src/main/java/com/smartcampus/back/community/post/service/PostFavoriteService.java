package com.smartcampus.back.community.post.service;

import com.smartcampus.back.community.post.entity.Post;
import com.smartcampus.back.community.post.entity.PostFavorite;
import com.smartcampus.back.community.post.repository.PostFavoriteRepository;
import com.smartcampus.back.community.post.repository.PostRepository;
import com.smartcampus.back.auth.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글 즐겨찾기 관련 서비스
 *
 * 사용자가 게시글을 즐겨찾기 등록하거나 해제하고,
 * 본인이 즐겨찾기한 게시글 목록을 조회하는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFavoriteService {

    private final PostFavoriteRepository postFavoriteRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 즐겨찾기 등록
     *
     * @param user 현재 사용자
     * @param postId 즐겨찾기할 게시글 ID
     */
    @Transactional
    public void addFavorite(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (postFavoriteRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("이미 즐겨찾기한 게시글입니다.");
        }

        postFavoriteRepository.save(PostFavorite.builder()
                .user(user)
                .post(post)
                .build());
    }

    /**
     * 게시글 즐겨찾기 해제
     *
     * @param user 현재 사용자
     * @param postId 즐겨찾기 해제할 게시글 ID
     */
    @Transactional
    public void removeFavorite(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        PostFavorite favorite = postFavoriteRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new EntityNotFoundException("즐겨찾기 내역이 없습니다."));

        postFavoriteRepository.delete(favorite);
    }

    /**
     * 사용자의 즐겨찾기한 게시글 목록 조회
     *
     * @param user 현재 사용자
     * @return 즐겨찾기한 게시글 목록
     */
    public List<PostFavorite> getUserFavorites(User user) {
        return postFavoriteRepository.findAllByUser(user);
    }
}
