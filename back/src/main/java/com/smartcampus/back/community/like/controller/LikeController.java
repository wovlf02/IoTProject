package com.smartcampus.back.community.like.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.community.like.service.LikeQueryService;
import com.smartcampus.back.community.like.service.LikeService;
import com.smartcampus.back.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * LikeController
 * <p>
 * 게시글, 댓글, 대댓글에 대한 좋아요 추가, 취소, 조회 API를 제공합니다.
 * </p>
 */
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeQueryService likeQueryService;

    /**
     * 좋아요 등록 (토글 방식으로 취소도 가능)
     *
     * @param targetId   좋아요 대상 ID (postId, commentId, replyId 중 하나)
     * @param targetType 좋아요 대상 타입 (POST, COMMENT, REPLY)
     * @param user       현재 로그인 사용자
     * @return 처리 결과 메시지
     */
    @PostMapping("/{targetType}/{targetId}")
    public ApiResponse<String> toggleLike(
            @PathVariable("targetId") Long targetId,
            @PathVariable("targetType") String targetType,
            @CurrentUser User user
    ) {
        return ApiResponse.success(likeService.toggleLike(user, targetType, targetId));
    }

    /**
     * 좋아요 개수 조회
     *
     * @param targetId   좋아요 대상 ID
     * @param targetType 좋아요 대상 타입
     * @return 해당 대상의 좋아요 수
     */
    @GetMapping("/{targetType}/{targetId}/count")
    public ApiResponse<Long> getLikeCount(
            @PathVariable("targetId") Long targetId,
            @PathVariable("targetType") String targetType
    ) {
        return ApiResponse.success(likeQueryService.getLikeCount(targetType, targetId));
    }

    /**
     * 현재 사용자가 해당 대상에 좋아요를 눌렀는지 여부 확인
     *
     * @param targetId   대상 ID
     * @param targetType 대상 타입
     * @param user       현재 사용자
     * @return true: 눌렀음 / false: 안눌렀음
     */
    @GetMapping("/{targetType}/{targetId}/check")
    public ApiResponse<Boolean> isLikedByUser(
            @PathVariable("targetId") Long targetId,
            @PathVariable("targetType") String targetType,
            @CurrentUser User user
    ) {
        return ApiResponse.success(likeQueryService.isLikedByUser(user, targetType, targetId));
    }
}
