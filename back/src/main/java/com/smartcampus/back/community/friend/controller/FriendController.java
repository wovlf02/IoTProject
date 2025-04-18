package com.smartcampus.back.community.friend.controller;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.annotation.CurrentUser;
import com.smartcampus.back.common.response.ApiResponse;
import com.smartcampus.back.community.friend.dto.request.FriendAcceptDto;
import com.smartcampus.back.community.friend.dto.request.FriendRequestDto;
import com.smartcampus.back.community.friend.dto.response.FriendSimpleResponse;
import com.smartcampus.back.community.friend.service.FriendBlockService;
import com.smartcampus.back.community.friend.service.FriendRequestService;
import com.smartcampus.back.community.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 친구 기능 처리 컨트롤러 (요청, 수락, 삭제, 차단 등)
 */
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final FriendRequestService friendRequestService;
    private final FriendBlockService friendBlockService;

    /**
     * 친구 요청을 보냅니다.
     *
     * @param user 현재 로그인 사용자
     * @param request 친구 요청 대상 ID
     * @return 처리 결과 메시지
     */
    @PostMapping("/request")
    public ApiResponse<FriendSimpleResponse> sendRequest(
            @CurrentUser User user,
            @RequestBody FriendRequestDto request
    ) {
        return ApiResponse.success(friendRequestService.sendFriendRequest(user, request));
    }

    /**
     * 친구 요청을 수락합니다.
     *
     * @param user 현재 로그인 사용자
     * @param request 친구 요청 수락 DTO
     * @return 처리 결과 메시지
     */
    @PostMapping("/accept")
    public ApiResponse<FriendSimpleResponse> acceptRequest(
            @CurrentUser User user,
            @RequestBody FriendAcceptDto request
    ) {
        return ApiResponse.success(friendRequestService.acceptFriendRequest(user, request));
    }

    /**
     * 친구 요청을 거절합니다.
     *
     * @param user 현재 로그인 사용자
     * @param request 친구 요청 수락 DTO (거절도 동일 DTO 사용)
     * @return 처리 결과 메시지
     */
    @PostMapping("/reject")
    public ApiResponse<FriendSimpleResponse> rejectRequest(
            @CurrentUser User user,
            @RequestBody FriendAcceptDto request
    ) {
        return ApiResponse.success(friendRequestService.rejectFriendRequest(user, request));
    }

    /**
     * 친구를 삭제합니다.
     *
     * @param user 현재 로그인 사용자
     * @param friendId 친구 ID
     * @return 처리 결과 메시지
     */
    @DeleteMapping("/{friendId}")
    public ApiResponse<FriendSimpleResponse> deleteFriend(
            @CurrentUser User user,
            @PathVariable Long friendId
    ) {
        return ApiResponse.success(friendService.deleteFriend(user, friendId));
    }

    /**
     * 친구를 차단합니다.
     *
     * @param user 현재 로그인 사용자
     * @param friendId 차단할 친구 ID
     * @return 처리 결과 메시지
     */
    @PostMapping("/block/{friendId}")
    public ApiResponse<FriendSimpleResponse> blockFriend(
            @CurrentUser User user,
            @PathVariable Long friendId
    ) {
        return ApiResponse.success(friendBlockService.blockFriend(user, friendId));
    }

    /**
     * 친구 차단을 해제합니다.
     *
     * @param user 현재 로그인 사용자
     * @param friendId 차단 해제할 친구 ID
     * @return 처리 결과 메시지
     */
    @DeleteMapping("/block/{friendId}")
    public ApiResponse<FriendSimpleResponse> unblockFriend(
            @CurrentUser User user,
            @PathVariable Long friendId
    ) {
        return ApiResponse.success(friendBlockService.unblockFriend(user, friendId));
    }
}
