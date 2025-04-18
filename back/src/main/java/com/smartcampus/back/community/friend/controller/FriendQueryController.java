package com.smartcampus.back.community.friend.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.community.friend.dto.response.BlockedFriendListResponse;
import com.smartcampus.back.community.friend.dto.response.FriendListResponse;
import com.smartcampus.back.community.friend.dto.response.FriendRequestListResponse;
import com.smartcampus.back.community.friend.dto.response.FriendSearchResponse;
import com.smartcampus.back.community.friend.service.FriendBlockService;
import com.smartcampus.back.community.friend.service.FriendQueryService;
import com.smartcampus.back.community.friend.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 친구 관련 조회 전용 API 컨트롤러
 */
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendQueryController {

    private final FriendQueryService friendQueryService;
    private final FriendRequestService friendRequestService;
    private final FriendBlockService friendBlockService;

    /**
     * 현재 로그인 사용자의 친구 목록을 조회합니다.
     *
     * @param user 현재 로그인 사용자
     * @return 친구 목록 응답
     */
    @GetMapping
    public ApiResponse<FriendListResponse> getFriendList(@CurrentUser User user) {
        return ApiResponse.success(friendQueryService.getFriendList(user));
    }

    /**
     * 받은 친구 요청 목록을 조회합니다.
     *
     * @param user 현재 로그인 사용자
     * @return 친구 요청 목록 응답
     */
    @GetMapping("/requests")
    public ApiResponse<FriendRequestListResponse> getReceivedRequests(@CurrentUser User user) {
        return ApiResponse.success(friendRequestService.getReceivedFriendRequests(user));
    }

    /**
     * 차단한 사용자 목록을 조회합니다.
     *
     * @param user 현재 로그인 사용자
     * @return 차단된 친구 목록 응답
     */
    @GetMapping("/blocked")
    public ApiResponse<BlockedFriendListResponse> getBlockedList(@CurrentUser User user) {
        return ApiResponse.success(friendBlockService.getBlockedFriends(user));
    }

    /**
     * 닉네임으로 사용자 검색 (친구 추가 목적)
     *
     * @param keyword 검색할 닉네임 키워드
     * @return 닉네임 기반 검색 결과 목록
     */
    @GetMapping("/search")
    public ApiResponse<FriendSearchResponse> searchUserByNickname(@RequestParam String keyword) {
        return ApiResponse.success(friendQueryService.searchUserByNickname(keyword));
    }
}
