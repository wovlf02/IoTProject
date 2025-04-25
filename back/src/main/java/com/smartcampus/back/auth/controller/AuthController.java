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
 * 인증 관련 API 컨트롤러
 * <p>
 * 회원가입, 로그인, 로그아웃, 토큰 재발급, 이메일 인증, 계정 복구 등의 기능 제공
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // ---------------- [회원가입 1단계] ----------------

    /**
     * 아이디 중복 여부 확인
     *
     * @param request UsernameCheckRequest (username: 확인할 아이디)
     * @return 중복 여부 (true = 사용 가능, false = 중복됨)
     */
    @PostMapping("/check-username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(@RequestBody @Valid UsernameCheckRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.isUsernameAvailable(request.getUsername())));
    }

    /**
     * 닉네임 중복 여부 확인
     *
     * @param request NicknameCheckRequest (nickname: 확인할 닉네임)
     * @return 중복 여부 (true = 사용 가능, false = 중복됨)
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponse<Boolean>> checkNickname(@RequestBody @Valid NicknameCheckRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.isNicknameAvailable(request.getNickname())));
    }

    /**
     * 이메일 인증 코드 발송
     *
     * @param request EmailSendRequest (email: 인증 메일을 보낼 이메일 주소)
     * @return 성공 메시지 (e.g. "코드 발송 완료")
     */
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<String>> sendVerificationCode(@RequestBody @Valid EmailSendRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.sendVerificationCode(request.getEmail())));
    }

    /**
     * 이메일 인증 코드 검증
     *
     * @param request EmailVerifyRequest (email, code 포함)
     * @return 인증 성공 여부 (true/false)
     */
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@RequestBody @Valid EmailVerifyRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.verifyCode(request.getEmail(), request.getCode())));
    }

    // ---------------- [회원가입 2단계] ----------------

    /**
     * 회원가입 최종 등록
     *
     * @param request RegisterRequest
     *        (username, password, nickname, email, 학년, 과목, 학습습관, 프로필사진 포함)
     * @return 가입 완료 여부
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // ---------------- [로그인 / 로그아웃 / 토큰 재발급] ----------------

    /**
     * 로그인
     *
     * @param request LoginRequest (username, password)
     * @return LoginResponse (accessToken, refreshToken, 사용자 정보)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
    }

    /**
     * 로그아웃
     *
     * @param request TokenRequest (refreshToken 포함)
     * @return 성공 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid TokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 토큰 재발급
     *
     * @param request TokenRequest (refreshToken 포함)
     * @return TokenResponse (새로운 accessToken)
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestBody @Valid TokenRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.reissue(request.getRefreshToken())));
    }

    // ---------------- [계정 복구 - 아이디 찾기] ----------------

    /**
     * [아이디 찾기] 이메일로 인증코드 발송
     *
     * @param request EmailRequest (이메일 주소)
     * @return 코드 발송 성공 메시지
     */
    @PostMapping("/find-username/send-code")
    public ResponseEntity<ApiResponse<String>> sendCodeToFindUsername(@RequestBody @Valid EmailRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.sendCodeToFindUsername(request.getEmail())));
    }

    /**
     * [아이디 찾기] 인증코드 검증 후 해당 이메일의 username 반환
     *
     * @param request EmailVerifyRequest (email, code)
     * @return 아이디 문자열
     */
    @PostMapping("/find-username/verify-code")
    public ResponseEntity<ApiResponse<String>> verifyCodeAndFindUsername(@RequestBody @Valid EmailVerifyRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.verifyAndFindUsername(request)));
    }

    // ---------------- [계정 복구 - 비밀번호 재설정] ----------------

    /**
     * [비밀번호 재설정] 본인 확인 후 인증코드 발송 (아이디+이메일 매칭)
     *
     * @param request PasswordResetRequest (username + email)
     * @return 코드 발송 성공 메시지
     */
    @PostMapping("/password/request")
    public ResponseEntity<ApiResponse<String>> requestPasswordReset(@RequestBody @Valid PasswordResetRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.sendCodeToResetPassword(request)));
    }

    /**
     * [비밀번호 재설정] 이메일 인증 코드 검증
     *
     * @param request EmailVerifyRequest (email + code)
     * @return 인증 성공 여부
     */
    @PostMapping("/password/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyPasswordResetCode(@RequestBody @Valid EmailVerifyRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.verifyCode(request.getEmail(), request.getCode())));
    }

    /**
     * [비밀번호 재설정] 새 비밀번호 저장
     *
     * @param request PasswordChangeRequest (username, newPassword)
     * @return 성공 응답
     */
    @PutMapping("/password/update")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody @Valid PasswordChangeRequest request) {
        authService.updatePassword(request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
