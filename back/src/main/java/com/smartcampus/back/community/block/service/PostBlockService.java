package com.smartcampus.back.community.block.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.entity.PostBlock;
import com.smartcampus.back.community.block.repository.PostBlockRepository;
import com.smartcampus.back.community.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 차단 관련 비즈니스 로직 처리 서비스
 */
@Service
@RequiredArgsConstructor
public class PostBlockService {

    private final PostBlockRepository postBlockRepository;

    /**
     * 게시글 차단
     *
     * @param user 차단 요청을 한 사용자
     * @param post 차단 대상 게시글
     */
    @Transactional
    public void block(User user, Post post) {
        boolean alreadyBlocked = postBlockRepository.findByUserAndPost(user, post).isPresent();
        if (!alreadyBlocked) {
            PostBlock block = PostBlock.builder()
                    .user(user)
                    .post(post)
                    .build();
            postBlockRepository.save(block);
        }
    }

    /**
     * 게시글 차단 해제
     *
     * @param user 요청 사용자
     * @param post 해제 대상 게시글
     */
    @Transactional
    public void unblock(User user, Post post) {
        postBlockRepository.findByUserAndPost(user, post)
                .ifPresent(postBlockRepository::delete);
    }
}
