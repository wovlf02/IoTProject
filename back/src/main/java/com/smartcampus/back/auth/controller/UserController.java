package com.smartcampus.back.auth.controller;

import com.smartcampus.back.auth.dto.request.NicknameUpdateRequest;
import com.smartcampus.back.auth.dto.request.PasswordChangeRequest;
import com.smartcampus.back.auth.dto.response.UserProfileResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.service.UserService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 정보 관리 컨트롤러
 * <p>
 * 로그인 후 사용자 프로필 조회, 닉네임 변경, 비밀번호 변경 등을 처리합니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 현재 로그인한 사용자 정보 조회
     *
     * @return 사용자 프로필 정보
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(UserProfileResponse.fromEntity(user)));
    }

    /**
     * 사용자 닉네임 변경
     *
     * @param request 닉네임 변경 요청 (새 닉네임)
     * @return 성공 응답
     */
    @PutMapping("/nickname")
    public ResponseEntity<ApiResponse<Void>> updateNickname(@RequestBody @Valid NicknameUpdateRequest request) {
        User user = userService.getCurrentUser();
        user.setNickname(request.getNickname());
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 사용자 비밀번호 변경
     *
     * @param request 비밀번호 변경 요청 (현재 비밀번호, 새 비밀번호)
     * @return 성공 응답
     */
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
