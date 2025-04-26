package com.smartcampus.back.auth.controller;

import com.smartcampus.back.auth.dto.request.PasswordChangeRequest;
import com.smartcampus.back.auth.dto.request.ProfileUpdateRequest;
import com.smartcampus.back.auth.dto.response.UserProfileResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.service.UserService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 * <p>
 * 로그인한 사용자의 정보 조회 및 수정 기능을 제공하는 컨트롤러입니다.
 * </p>
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 현재 로그인한 사용자 프로필 조회
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(UserProfileResponse.fromEntity(user)));
    }

    /**
     * username으로 사용자 조회
     */
    @GetMapping("/find-by-username")
    public ResponseEntity<ApiResponse<UserProfileResponse>> findByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(UserProfileResponse.fromEntity(user)));
    }

    /**
     * email로 사용자 조회
     */
    @GetMapping("/find-by-email")
    public ResponseEntity<ApiResponse<UserProfileResponse>> findByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(UserProfileResponse.fromEntity(user)));
    }

    /**
     * nickname으로 사용자 조회
     */
    @GetMapping("/find-by-nickname")
    public ResponseEntity<ApiResponse<UserProfileResponse>> findByNickname(@RequestParam String nickname) {
        User user = userService.getUserByNickname(nickname);
        return ResponseEntity.ok(ApiResponse.success(UserProfileResponse.fromEntity(user)));
    }

    /**
     * 사용자 비밀번호 변경
     */
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        User user = userService.getCurrentUser();
        user.setPassword(request.getNewPassword()); // 실제로는 PasswordUtil로 암호화 필요!
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 사용자 프로필 수정 (닉네임, 프로필 이미지)
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@RequestBody @Valid ProfileUpdateRequest request) {
        User user = userService.getCurrentUser();
        user.setNickname(request.getNickname());
        user.setProfileImageUrl(request.getProfileImageUrl());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
