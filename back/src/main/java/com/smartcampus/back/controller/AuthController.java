package com.smartcampus.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


}
