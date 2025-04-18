package com.smartcampus.back.community.comment.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.community.block.repository.CommentBlockRepository;
import com.smartcampus.back.community.comment.dto.response.CommentListResponse;
import com.smartcampus.back.community.comment.dto.response.CommentResponse;
import com.smartcampus.back.community.comment.entity.Comment;
import com.smartcampus.back.community.comment.repository.CommentQueryRepository;
import com.smartcampus.back.community.like.repository.LikeRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 댓글 및 대댓글 계층형 조회, 정렬, 차단 필터링을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;
    private final CommentBlockRepository commentBlockRepository;
    private final LikeRepository likeRepository;

    /**
     * 특정 게시글에 달린 댓글 및 대댓글 목록을 계층형으로 조회합니다.
     * - 차단된 댓글 제외
     * - 대댓글 포함
     * - 좋아요 수 포함
     *
     * @param user     로그인 사용자
     * @param postId   대상 게시글 ID
     * @return 계층형 댓글 목록
     */
    @Transactional(readOnly = true)
    public CommentListResponse getCommentsWithReplies(User user, Long postId) {
        // 댓글/대댓글 모두 한 번에 조회
        List<Comment> comments = commentQueryRepository.findAllByPostId(postId);

        if (comments.isEmpty()) {
            return new CommentListResponse(Collections.emptyList());
        }

        // 차단된 댓글 ID
        Set<Long> blockedCommentIds = commentBlockRepository.findBlockedCommentIdsByUserId(user.getId());

        // 댓글 ID → 좋아요 수 Map
        Map<Long, Long> likeCountMap = likeRepository.countLikesByCommentIds(
                comments.stream().map(Comment::getId).collect(Collectors.toSet())
        );

        // 댓글 ID → 사용자가 좋아요 눌렀는지 여부
        Set<Long> likedCommentIds = likeRepository.findLikedCommentIdsByUserAndCommentIds(user, likeCountMap.keySet());

        // 댓글 ID → CommentResponse 변환
        Map<Long, CommentResponse> responseMap = new HashMap<>();
        for (Comment comment : comments) {
            if (blockedCommentIds.contains(comment.getId())) continue;

            boolean liked = likedCommentIds.contains(comment.getId());
            long likeCount = likeCountMap.getOrDefault(comment.getId(), 0L);

            CommentResponse response = CommentResponse.of(comment, liked, likeCount);
            responseMap.put(comment.getId(), response);
        }

        // 계층 구조 구성
        List<CommentResponse> result = new ArrayList<>();
        for (CommentResponse response : responseMap.values()) {
            if (response.getParentId() == null) {
                result.add(response); // 최상위 댓글
            } else {
                CommentResponse parent = responseMap.get(response.getParentId());
                if (parent != null) {
                    parent.getReplies().add(response); // 대댓글로 추가
                }
            }
        }

        // 정렬 (최신순, 좋아요순 등 필요 시 추가)
        result.sort(Comparator.comparing(CommentResponse::getCreatedAt));

        return new CommentListResponse(result);
    }

    /**
     * 단일 댓글 조회
     */
    @Transactional(readOnly = true)
    public Comment getCommentOrThrow(Long commentId) {
        return commentQueryRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }
}
