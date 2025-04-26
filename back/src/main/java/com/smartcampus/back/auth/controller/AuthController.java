package com.smartcampus.back.auth.controller;

import com.smartcampus.back.auth.dto.request.*;
import com.smartcampus.back.auth.dto.response.LoginResponse;
import com.smartcampus.back.auth.dto.response.TokenResponse;
import com.smartcampus.back.auth.service.AuthService;
import com.smartcampus.back.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
 * <p>
 * 인증(Authentication) 및 계정 복구 관련 기능을 제공하는 컨트롤러입니다.
 * 회원가입, 로그인, 로그아웃, 토큰 재발급, 아이디/비밀번호 찾기 기능을 담당합니다.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ------------------ 회원가입: 기본 정보 & 이메일 인증 ------------------

    /**
     * 아이디 중복 확인
     */
    @PostMapping("/check-username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(@RequestBody @Valid UsernameCheckRequest request) {
        boolean isAvailable = authService.isUsernameAvailable(request.getUsername());
        return ResponseEntity.ok(ApiResponse.success(isAvailable));
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponse<Boolean>> checkNickname(@RequestBody @Valid NicknameCheckRequest request) {
        boolean isAvailable = authService.isNicknameAvailable(request.getNickname());
        return ResponseEntity.ok(ApiResponse.success(isAvailable));
    }

    /**
     * 이메일 인증코드 발송
     */
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<String>> sendEmailCode(@RequestBody @Valid EmailSendRequest request) {
        String code = authService.sendEmailCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(code));
    }

    /**
     * 이메일 인증코드 검증
     */
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyEmailCode(@RequestBody @Valid EmailVerifyRequest request) {
        boolean verified = authService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiResponse.success(verified));
    }

    // ------------------ 회원가입: 프로필 & 학습 정보 등록 ------------------

    /**
     * 회원가입 최종 제출
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // ------------------ 로그인 / 로그아웃 / 토큰 ------------------

    /**
     * 로그인 요청
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 로그아웃 요청
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid TokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 토큰 재발급 요청
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissueToken(@RequestBody @Valid TokenRequest request) {
        TokenResponse response = authService.reissueToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ------------------ 계정 / 비밀번호 복구 ------------------

    /**
     * [아이디 찾기] 인증코드 발송
     */
    @PostMapping("/find-username/send-code")
    public ResponseEntity<ApiResponse<String>> sendFindUsernameCode(@RequestBody @Valid EmailRequest request) {
        String code = authService.sendFindUsernameCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(code));
    }

    /**
     * [아이디 찾기] 인증코드 검증 및 아이디 반환
     */
    @PostMapping("/find-username/verify-code")
    public ResponseEntity<ApiResponse<String>> verifyFindUsernameCode(@RequestBody @Valid EmailVerifyRequest request) {
        String username = authService.verifyFindUsernameCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiResponse.success(username));
    }

    /**
     * [비밀번호 재설정] 본인확인 및 코드 발송
     */
    @PostMapping("/password/request")
    public ResponseEntity<ApiResponse<String>> sendPasswordResetCode(@RequestBody @Valid PasswordResetRequest request) {
        String code = authService.sendPasswordResetCode(request.getUsername(), request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(code));
    }

    /**
     * [비밀번호 재설정] 인증코드 검증
     */
    @PostMapping("/password/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyPasswordResetCode(@RequestBody @Valid EmailVerifyRequest request) {
        boolean verified = authService.verifyPasswordResetCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiResponse.success(verified));
    }

    /**
     * [비밀번호 재설정] 새 비밀번호 저장
     */
    @PutMapping("/password/update")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody @Valid PasswordChangeRequest request) {
        authService.updatePassword(request.getUsername(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
