package com.smartcampus.back.admin.controller;

import com.smartcampus.back.admin.dto.request.UserStatusUpdateRequest;
import com.smartcampus.back.admin.dto.response.UserListResponse;
import com.smartcampus.back.admin.service.AdminUserService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 전용 사용자 관리 컨트롤러
 * <p>
 * 전체 사용자 목록 조회, 사용자 상태(활성/정지 등) 변경 기능 제공
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 전체 사용자 목록 페이징 조회
     *
     * @param status   필터링할 사용자 상태 (ex: ACTIVE, SUSPENDED) - 선택 파라미터
     * @param pageable 페이징 정보 객체 (page, size 등 포함)
     * @return 사용자 목록 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<UserListResponse>> getUsers(
            @RequestParam(required = false) String status,
            Pageable pageable
    ) {
        UserListResponse users = adminUserService.getUserList(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * 특정 사용자 상태 변경 (활성화 / 정지 등)
     *
     * @param userId  상태를 변경할 대상 사용자 ID
     * @param request 사용자 상태 변경 요청 DTO (변경할 상태값 포함)
     * @return 처리 성공 응답
     */
    @PutMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody UserStatusUpdateRequest request
    ) {
        adminUserService.updateUserStatus(userId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
