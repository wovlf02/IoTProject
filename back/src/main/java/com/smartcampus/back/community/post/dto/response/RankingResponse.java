package com.smartcampus.back.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 활동 순위 응답 DTO
 * <p>
 * 게시글 및 댓글 수 기준으로 정렬된 사용자 활동 순위 목록을 포함합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingResponse {

    /**
     * 사용자 랭킹 리스트
     */
    private List<UserRankingInfo> rankings;

    /**
     * 사용자 한 명의 활동 지표 정보
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRankingInfo {
        private Long userId;
        private String nickname;
        private String profileImageUrl;
        private int postCount;
        private int commentCount;
        private int totalScore; // 예: post 1점 + comment 0.5점 기준으로 계산된 활동 점수
    }
}
