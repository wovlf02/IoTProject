package com.smartcampus.back.controller;

import com.smartcampus.back.entity.FriendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FriendController - 친구 관리 컨트롤러
 *
 * 사용자 검색, 친구 요청, 수락/거절, 친구 목록 관리, 친구 삭제, 차단, 신고 기능 포함
 */
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // ========================= 1. 친구 검색 API =============================

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String username) {
        List<UserResponse> users = friendService.searchUsers(username);
        return ResponseEntity.ok(users);
    }

    // ========================= 2. 친구 요청 API =============================

    /**
     * [친구 요청 전송]
     *
     * 특정 사용자에게 친구 요청을 전송
     *
     * @param request 친구 요청 정보 (수신자 ID 포함)
     * @return 요청 성공 메시지
     */
    @PostMapping("/request")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest request) {
        friendService.sendFriendRequest(request);
        return ResponseEntity.ok("친구 요청이 전송되었습니다.");
    }

    /**
     * [친구 요청 목록 확인]
     *
     * 사용자가 받은 친구 요청 목록을 시간 순으로 조회
     *
     * @return 수신된 친구 요청 목록
     */
    @GetMapping("/requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FriendRequestResponse>> getReceivedFriendRequests() {
        List<FriendRequestResponse> requests = friendService.getReceivedFriendRequests();
        return ResponseEntity.ok(requests);
    }

    /**
     * [친구 요청 수락]
     *
     * 사용자가 수신한 친구 요청을 수락하여 친구 목록에 추가
     *
     * @param requestId 친구 요청 ID
     * @return 수락 성공 메시지
     */
    @PostMapping("/request/{requestId}/accept")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long requestId) {
        friendService.acceptFriendRequest(requestId);
        return ResponseEntity.ok("친구 요청을 수락하였습니다.");
    }

    /**
     * [친구 요청 거절]
     *
     * 사용자가 수신한 친구 요청을 거절
     *
     * @param requestId 친구 요청 ID
     * @return 거절 성공 메시지
     */
    @PostMapping("/request/{requestId}/reject")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long requestId) {
        friendService.rejectFriendRequest(requestId);
        return ResponseEntity.ok("친구 요청을 거절하였습니다.");
    }

    // ============================ 3. 친구 목록 API ==============================

    /**
     * [친구 목록 확인]
     *
     * 사용자의 친구 목록을 조회 (프로필 아이디, 온라인 상태 포함)
     *
     * @return 친구 목록
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FriendResponse>> getFriendList() {
        List<FriendResponse> friends = friendService.getFriendList();
        return ResponseEntity.ok(friends);
    }

    // ============================ 4. 친구 삭제 API ==============================

    /**
     * [친구 삭제]
     * 
     * 특정 친구와의 관계 해제 (양측 목록에서 삭제됨)
     * 
     * @param friendId 친구 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{friendId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendId) {
        friendService.deleteFriend(friendId);
        return ResponseEntity.ok("친구가 삭제되었습니다.");
    }

    // ============================ 4. 친구 차단 API ==============================

    /**
     * [친구 차단]
     * 
     * 특정 사용자를 차단하여 모든 상호작용 차단
     * 
     * @param friendId 차단할 친구 ID
     * @return 차단 성공 메시지
     */
    @PostMapping("/{friendId}/block")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> blockFriend(@PathVariable Long friendId) {
        friendService.blockFriend(friendId);
        return ResponseEntity.ok("사용자가 차단되었습니다.");
    }

    // ============================ 4. 친구 차단 API ==============================

    /**
     * [친구 신고]
     * 
     * 부적절한 사용자를 신고 (신고 사유 포함)
     * 
     * @param friendId 신고 대상 친구 ID
     * @param request 신고 요청 DTO
     * @return 신고 성공 메시지
     */
    @PostMapping("/{friendId}/report")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> reportFriend(
            @PathVariable Long friendId,
            @RequestBody ReportRequest request
    ) {
        friendService.reportFriend(friendId, request);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }
}
