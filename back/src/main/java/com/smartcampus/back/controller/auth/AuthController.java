package com.smartcampus.back.controller.auth;

import com.smartcampus.back.dto.auth.request.*;
import com.smartcampus.back.dto.auth.response.*;
import com.smartcampus.back.global.dto.response.ApiResponse;
import com.smartcampus.back.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 API를 제공하는 컨트롤러 클래스입니다.
 * 회원가입, 로그인, 중복 확인, 이메일 인증, 비밀번호 재설정, 사용자 정보 조회 및 수정 등을 처리합니다.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 아이디 중복 여부를 확인합니다.
     *
     * @param request 아이디를 담은 요청 DTO
     * @return 사용 가능 여부를 포함한 ApiResponse
     */
    @PostMapping("/check-username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(@RequestBody @Valid UsernameCheckRequest request) {
        boolean available = authService.isUsernameAvailable(request.getUsername());
        return ResponseEntity.ok(ApiResponse.success(available));
    }

    /**
     * 닉네임 중복 여부를 확인합니다.
     *
     * @param request 닉네임을 담은 요청 DTO
     * @return 사용 가능 여부를 포함한 ApiResponse
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponse<Boolean>> checkNickname(@RequestBody @Valid NicknameCheckRequest request) {
        boolean available = authService.isNicknameAvailable(request.getNickname());
        return ResponseEntity.ok(ApiResponse.success(available));
    }

    /**
     * 이메일 중복 여부를 확인합니다.
     *
     * @param request 이메일을 담은 요청 DTO
     * @return 사용 가능 여부를 포함한 ApiResponse
     */
    @PostMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@RequestBody @Valid EmailCheckRequest request) {
        boolean available = authService.isEmailAvailable(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(available));
    }

    /**
     * 이메일 인증 코드를 발송합니다.
     *
     * @param request 이메일을 담은 요청 DTO
     * @return 발송된 인증 코드 식별자 또는 메시지를 담은 ApiResponse
     */
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<String>> sendEmailCode(@RequestBody @Valid EmailSendRequest request) {
        String result = authService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 이메일 인증 코드를 검증합니다.
     *
     * @param request 이메일과 인증 코드를 담은 요청 DTO
     * @return 인증 성공 여부를 담은 ApiResponse
     */
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyEmailCode(@RequestBody @Valid EmailVerifyRequest request) {
        boolean isValid = authService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiResponse.success(isValid));
    }

    /**
     * 회원가입을 수행합니다.
     *
     * @param request 회원가입 정보(아이디, 닉네임, 비밀번호, 이메일, 시간표 등)를 담은 DTO
     * @return 성공 시 빈 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 로그인을 수행하고 JWT 토큰을 반환합니다.
     *
     * @param request 아이디 및 비밀번호 정보를 담은 요청 DTO
     * @return JWT 액세스/리프레시 토큰을 담은 응답 DTO
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse token = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    /**
     * 로그아웃을 수행합니다. 서버에 저장된 Refresh Token을 삭제합니다.
     *
     * @param authorization JWT AccessToken (헤더에서 전달)
     * @return 성공 시 빈 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authorization) {
        authService.logout(authorization);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 비밀번호 재설정용 이메일 인증 코드를 발송합니다.
     *
     * @param request 이메일을 담은 요청 DTO
     * @return 발송 결과 메시지
     */
    @PostMapping("/reset-password/send-code")
    public ResponseEntity<ApiResponse<String>> sendPasswordResetCode(@RequestBody @Valid PasswordResetEmailRequest request) {
        String result = authService.sendPasswordResetCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 비밀번호를 재설정합니다.
     *
     * @param request 이메일, 인증코드, 새 비밀번호를 담은 요청 DTO
     * @return 성공 시 빈 응답
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 내 정보(로그인 사용자 정보)를 조회합니다.
     *
     * @return 현재 로그인된 사용자의 정보 DTO
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo() {
        MyInfoResponse userInfo = authService.getMyInfo();
        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }

    /**
     * 내 정보를 수정합니다 (닉네임, 비밀번호 등).
     *
     * @param request 변경할 사용자 정보를 담은 요청 DTO
     * @return 성공 시 빈 응답
     */
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateMyInfo(@RequestBody @Valid MyInfoUpdateRequest request) {
        authService.updateMyInfo(request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
