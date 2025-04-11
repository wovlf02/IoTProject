package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.like.LikeResponse;
import com.smartcampus.back.post.entity.Like;
import com.smartcampus.back.post.entity.Post;
import com.smartcampus.back.post.exception.PostNotFoundException;
import com.smartcampus.back.post.repository.LikeRepository;
import com.smartcampus.back.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 좋아요 관련 서비스 클래스
 * 좋아요 생성/취소 및 개수 조회, 사용자 상태 확인 처리
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 좋아요 상태를 토글
     * 사용자가 이미 좋아요 누른 경우 -> 좋아요 취소
     * 누르지 않은 경우 -> 좋아요 추가
     *
     * @param postId 게시글 ID
     * @param userId 사용자 ID
     * @return 현재 좋아요 상태 및 총 개수를 포함한 응답
     */
    public LikeResponse toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        if(alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
        } else {
            Like like = Like.builder()
                    .userId(userId)
                    .post(post)
                    .build();
            likeRepository.save(like);
        }

        long totalLikes = likeRepository.countByPostId(postId);

        return LikeResponse.builder()
                .liked(!alreadyLiked)
                .totalLikes((int) totalLikes)
                .message(alreadyLiked ? "좋아요가 취소되었습니다." : "좋아요를 추가했습니다.")
                .build();
    }

    /**
     * 게시글의 총 좋아요 수를 반환
     *
     * @param postId 게시글 ID
     * @return 좋아요 수
     */
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    /**
     * 사용자가 해당 게시글에 좋아요를 눌렀는지 여부 확인
     *
     * @param postId 게시글 ID
     * @param userId 사용자 ID
     * @return true: 좋아요 눌렀음, false: 안 눌렀음
     */
    public boolean isPostLiked(Long postId, Long userId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
