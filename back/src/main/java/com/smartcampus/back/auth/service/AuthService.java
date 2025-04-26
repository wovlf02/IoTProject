package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.*;
import com.smartcampus.back.auth.dto.response.LoginResponse;
import com.smartcampus.back.auth.dto.response.TokenResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.auth.util.EmailUtil;
import com.smartcampus.back.auth.util.PasswordUtil;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.auth.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

/**
 * AuthService
 * <p>
 * 회원가입, 로그인, 토큰 재발급, 아이디/비밀번호 복구 등 인증 관련 비즈니스 로직을 처리하는 서비스입니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final PasswordUtil passwordUtil;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;

    private static final String EMAIL_AUTH_PREFIX = "emailAuth:";
    private static final long EMAIL_AUTH_EXPIRE_SECONDS = 300; // 5분

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    // ---------------------- 회원가입: 기본 정보 확인 ----------------------

    /**
     * 아이디 중복 여부 확인
     */
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    /**
     * 닉네임 중복 여부 확인
     */
    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    /**
     * 이메일 인증코드 발송
     */
    public String sendEmailCode(String email) {
        String code = emailUtil.createAuthCode();
        emailUtil.sendAuthCode(email, code);

        redisTemplate.opsForValue().set(EMAIL_AUTH_PREFIX + email, code, Duration.ofSeconds(EMAIL_AUTH_EXPIRE_SECONDS));
        return code;
    }

    /**
     * 이메일 인증코드 검증
     */
    public boolean verifyEmailCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(EMAIL_AUTH_PREFIX + email);

        if (savedCode == null || !savedCode.equals(code)) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_CODE);
        }

        redisTemplate.delete(EMAIL_AUTH_PREFIX + email);
        return true;
    }

    // ---------------------- 회원가입: 최종 등록 ----------------------

    /**
     * 회원가입 최종 제출
     */
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordUtil.encode(request.getPassword()))
                .nickname(request.getNickname())
                .email(request.getEmail())
                .profileImageUrl(request.getProfileImageUrl())
                .status(User.Status.ACTIVE)
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
    }

    // ---------------------- 로그인 / 로그아웃 / 토큰 ----------------------

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, Duration.ofDays(14));

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    /**
     * 로그아웃
     */
    public void logout(String refreshToken) {
        User user = jwtProvider.getUserFromToken(refreshToken);
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + user.getId());
    }

    /**
     * 토큰 재발급
     */
    public TokenResponse reissueToken(String refreshToken) {
        User user = jwtProvider.getUserFromToken(refreshToken);

        String savedRefreshToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + user.getId());
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtProvider.generateAccessToken(user);
        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    // ---------------------- 계정/비밀번호 복구 ----------------------

    /**
     * [아이디 찾기] 인증코드 발송
     */
    public String sendFindUsernameCode(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        String code = emailUtil.createAuthCode();
        emailUtil.sendAuthCode(email, code);

        redisTemplate.opsForValue().set(EMAIL_AUTH_PREFIX + email, code, Duration.ofSeconds(EMAIL_AUTH_EXPIRE_SECONDS));
        return code;
    }

    /**
     * [아이디 찾기] 인증코드 검증 및 아이디 반환
     */
    public String verifyFindUsernameCode(String email, String code) {
        verifyEmailCode(email, code);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getUsername();
    }

    /**
     * [비밀번호 재설정] 본인확인 인증코드 발송
     */
    public String sendPasswordResetCode(String username, String email) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getEmail().equals(email)) {
            throw new CustomException(ErrorCode.EMAIL_NOT_MATCH);
        }

        String code = emailUtil.createAuthCode();
        emailUtil.sendAuthCode(email, code);

        redisTemplate.opsForValue().set(EMAIL_AUTH_PREFIX + email, code, Duration.ofSeconds(EMAIL_AUTH_EXPIRE_SECONDS));
        return code;
    }

    /**
     * [비밀번호 재설정] 인증코드 검증
     */
    public boolean verifyPasswordResetCode(String email, String code) {
        return verifyEmailCode(email, code);
    }

    /**
     * [비밀번호 재설정] 새 비밀번호 저장
     */
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(passwordUtil.encode(newPassword));
    }
}
