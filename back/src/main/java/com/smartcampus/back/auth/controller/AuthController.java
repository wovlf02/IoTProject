package com.smartcampus.back.auth.controller;

import com.smartcampus.back.auth.dto.request.*;
import com.smartcampus.back.auth.dto.response.*;
import com.smartcampus.back.auth.service.AuthService;
import com.smartcampus.back.auth.service.EmailService;
import com.smartcampus.back.auth.service.PasswordResetService;
import com.smartcampus.back.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 인증 관련 요청을 처리하는 컨트롤러 클래스
 *
 * 회원가입, 로그인, 이메일 인증, 아이디/닉네임 중복 확인, 아이디 찾기, 비밀번호 재설정 기능 제공
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;

    /**
     * 아이디 중복 여부 확인
     * @param request 중복 확인할 아이디
     * @return 사용 가능 여부
     */
    @PostMapping("/check-username")
    public ResponseEntity<ApiResponse<UsernameCheckResponse>> checkUsername(
            @RequestBody @Valid UsernameCheckRequest request
    ) {
        UsernameCheckResponse response = authService.checkUsername(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 닉네임 중복 여부 확인
     * @param request 중복 확인할 닉네임
     * @return 사용 가능 여부
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponse<NicknameCheckResponse>> checkNickname(
            @RequestBody @Valid NicknameCheckRequest request
    ) {
        NicknameCheckResponse response = authService.checkNickname(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 이메일 인증 코드 전송
     * @param request 이메일 주소
     * @return 전송 완료 메시지
     */
    @PostMapping("/send-email-code")
    public ResponseEntity<ApiResponse<EmailVerificationResponse>> sendEmailCode(
            @RequestBody @Valid EmailVerificationRequest request
    ) {
        EmailVerificationResponse response = emailService.sendVerificationCode(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 이메일 인증 코드 검증
     * @param request 이메일 + 인증 코드
     * @return 검증 성공 여부
     */
    @PostMapping("/verify-email-code")
    public ResponseEntity<ApiResponse<VerifyEmailResponse>> verifyEmailCode(
            @RequestBody @Valid EmailVerificationRequest request
    ) {
        VerifyEmailResponse response = emailService.verifyCode(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 로그인 (JWT 토큰 발급)
     * @param request 아이디 + 비밀번호
     * @return Access Token, Refresh Token, 사용자 정보(아이디, 닉네임, 이메일)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 회원가입
     * @param request 회원가입 정보
     * @param profileImage 프로필 이미지 (선택)
     * @return 가입 완료 응답
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register (
            @ModelAttribute @Valid RegisterRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        RegisterResponse response = authService.register(request, profileImage);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 이메일 기반 아이디 찾기
     * @param request 이메일 정보
     * @return 아이디 정보
     */
    @PostMapping("/find-username")
    public ResponseEntity<ApiResponse<UsernameFindResponse>> findUsername(
            @RequestBody @Valid UsernameFindRequest request
    ) {
        UsernameFindResponse response = passwordResetService.findUsername(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 비밀번호 재설정 요청
     * @param request 이메일, 아이디, 새 비밀번호
     * @return 재설정 결과
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<ResetPasswordResponse>> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        ResetPasswordResponse response = passwordResetService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
