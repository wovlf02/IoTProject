package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.*;
import com.smartcampus.back.auth.dto.response.LoginResponse;
import com.smartcampus.back.auth.dto.response.TokenResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.global.security.JwtTokenProvider;
import com.smartcampus.back.global.util.EmailUtil;
import com.smartcampus.back.global.util.RandomCodeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 인증 서비스
 * <p>
 * 회원가입, 로그인, 이메일 인증, 계정 복구, 토큰 관리 등 인증 전반을 담당합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;
    private final EmailUtil emailUtil;

    private static final String EMAIL_CODE_PREFIX = "email:code:";

    /**
     * 아이디 중복 확인
     */
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    /**
     * 닉네임 중복 확인
     */
    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    /**
     * 이메일 인증코드 전송 (회원가입/아이디 찾기/비번 재설정 공용)
     */
    public String sendVerificationCode(String email) {
        String code = RandomCodeUtil.generate6DigitCode();
        redisTemplate.opsForValue().set(EMAIL_CODE_PREFIX + email, code, 5, TimeUnit.MINUTES);
        emailUtil.sendCode(email, code);
        return "인증코드가 이메일로 전송되었습니다.";
    }

    /**
     * 이메일 인증코드 검증
     */
    public boolean verifyCode(String email, String code) {
        String saved = redisTemplate.opsForValue().get(EMAIL_CODE_PREFIX + email);
        return saved != null && saved.equals(code);
    }

    /**
     * 회원가입 최종 등록
     */
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        if (userRepository.existsByNickname(request.getNickname()))
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImageUrl(request.getProfileImageUrl())
                .status(User.Status.ACTIVE)
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인 → access & refresh 토큰 발급
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        // Redis에 refresh token 저장 (7일 유효)
        redisTemplate.opsForValue().set("refresh:" + user.getUsername(), refreshToken, 7, TimeUnit.DAYS);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    /**
     * 로그아웃 → Redis에서 refresh 토큰 삭제
     */
    public void logout(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        redisTemplate.delete("refresh:" + username);
    }

    /**
     * accessToken 재발급
     */
    public TokenResponse reissue(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String saved = redisTemplate.opsForValue().get("refresh:" + username);
        if (!refreshToken.equals(saved)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    /**
     * 아이디 찾기 - 이메일로 인증코드 발송
     */
    public String sendCodeToFindUsername(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return sendVerificationCode(email);
    }

    /**
     * 아이디 찾기 - 인증코드 검증 후 아이디 반환
     */
    public String verifyAndFindUsername(EmailVerifyRequest request) {
        if (!verifyCode(request.getEmail(), request.getCode())) {
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getUsername();
    }

    /**
     * 비밀번호 재설정 코드 발송
     */
    public String sendCodeToResetPassword(PasswordResetRequest request) {
        userRepository.findByUsernameAndEmail(request.getUsername(), request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return sendVerificationCode(request.getEmail());
    }

    /**
     * 비밀번호 재설정 - 새 비밀번호 저장
     */
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(newPassword));
    }
}
