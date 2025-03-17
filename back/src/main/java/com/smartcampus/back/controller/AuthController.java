package com.smartcampus.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController - 사용자 인증 관련 컨트롤러
 *
 * 회원가입, 로그인, 아이디 찾기, 비밀번호 재설정 기능을 제공
 * 인증번호 발송 및 검증 API 포함
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ========================== 1. 회원가입 관련 API ==============================

    /**
     * [회원가입] 아이디 중복 확인
     *
     * @param request 아이디(username) 정보
     * @return 중복 여부 (true: 중복됨, false: 사용 가능)
     */
    @PostMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestBody UsernameCheckRequest request) {
        boolean isExists = authService.isUsernameExists(request.getUsername());
        return ResponseEntity.ok(isExists);
    }

    /**
     * [회원가입] 닉네임 중복 확인
     *
     * @param request 닉네임 정보
     * @return 중복 여부 (true: 중복됨, false: 사용 가능)
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody NicknameCheckRequest request) {
        boolean isExists = authService.isNicknameExists(request.getNickname());
        return ResponseEntity.ok(isExists);
    }

    /**
     * [회원가입] 이메일 중복 확인
     *
     * @param request 이메일 정보
     * @return 중복 여부 (true: 중복됨, false: 사용 가능)
     */
    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody EmailCheckRequest request) {
        boolean isExists = authService.isEmailExists(request.getEmail());
        return ResponseEntity.ok(isExists);
    }

    /**
     * [회원가입] 이메일 인증번호 발송
     *
     * @param request 이메일 정보
     * @return 이메일 발송 결과 메시지
     */
    @PostMapping("/send-email-verification")
    public ResponseEntity<String> sendEmailVerification(@RequestBody EmailCheckRequest request) {
        authService.sendEmailVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 이메일로 발송되었습니다.");
    }

    /**
     * [회원가입] 이메일 인증번호 검증
     *
     * @param request 이메일 & 인증번호 정보
     * @return 인증 결과 (true: 인증 성공, false: 인증 실패)
     */
    @PostMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@RequestBody EmailVerificationRequest request) {
        boolean isValid = authService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(isValid);
    }

    /**
     * [회원가입] 최종 회원가입 처리
     *
     * @param request 회원가입 정보 (아이디, 비밀번호, 이메일, 닉네임 등)
     * @return 회원가입 결과 메시지
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // ============================== 2. 로그인 관련 API =================================

    /**
     * [로그인] 일반 로그인
     *
     * @param request 로그인 요청 정보 (아이디, 비밀번호)
     * @return JWT 토큰 정보
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * [로그인] 카카오 소셜 로그인 처리
     *
     * @param request 카카오 로그인 요청 정보
     * @return 로그인 처리 결과
     */
    @PostMapping("/login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody SocialLoginRequest request) {
        LoginResponse response = authService.kakaoLogin(request);
        return ResponseEntity.ok(response);
    }

    /**
     * [로그인] 구글 소셜 로그인 처리
     *
     * @param request 구글 로그인 요청 정보
     * @return 로그인 처리 결과
     */
    @PostMapping("/login/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody SocialLoginRequest request) {
        LoginResopnse resopnse = authService.googleLgoin(request);
        return ResponseEntity.ok(resopnse);
    }

    // ============================== 3. 아이디 찾기 관련 API ==============================

    /**
     * [아이디 찾기] 이메일 인증번호 발송
     *
     * @param request 이메일 정보
     * @return 이메일 발송 결과 메시지
     */
    @PostMapping("/find-username/send-code")
    public ResponseEntity<String> sendUsernameRecoveryCode(@RequestBody EmailCheckRequest request) {
        authService.sendUsernameRecoveryCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 이메일로 발송되었습니다.");
    }

    /**
     * [아이디 찾기] 인증번호 검증 후 아이디 반환
     *
     * @param request 이메일 & 인증번호 정보
     * @return 찾은 아이디
     */
    @PostMapping("/find-username/verify-code")
    public ResponseEntity<String> verifyUsernameRecovery(@RequestBody EmailVerificationRequest request) {
        String username = authService.verifyUsernameRecovery(request.getEmail(), request.getCode());
        return ResponseEntity.ok(username);
    }

    // ============================ 4. 비밀번호 재설정 관련 API =============================

    /**
     * [비밀번호 재설정] 이메일 인증번호 발송
     *
     * @param request 이메일 정보
     * @return 이메일 발송 결과 메시지
     */
    @PostMapping("/reset-password/send-code")
    public ResponseEntity<String> sendPasswordResetCode(@RequestBody EmailCheckRequest request) {
        authService.sendPasswordResetCode(request.getEmail());
        return ResponseEntity.ok("비밀번호 재설정을 위한 인증번호가 이메일로 발송되었습니다.");
    }

    /**
     * [비밀번호 재설정] 인증번호 검증
     *
     * @param request 이메일 & 인증번호 정보
     * @return 인증 결과 (true: 인증 성공, false: 인증 실패)
     */
    @PostMapping("/reset-password/verify-code")
    public ResponseEntity<Boolean> verifyPasswordResetCode(@RequestBody EmailVerificationRequest request) {
        boolean isValid = authService.verifyPasswordResetCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(isValid);
    }

    /**
     * [비밀번호 재설정] 최종 비밀번호 변경
     *
     * @param request 비밀번호 변경 정보 (이메일, 새 비밀번호)
     * @return 비밀번호 변경 결과 메시지
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Validated @RequestBody PasswordResetRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}
