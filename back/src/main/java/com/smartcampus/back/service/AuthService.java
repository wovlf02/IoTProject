package com.smartcampus.back.service;

import com.smartcampus.back.config.JwtProvider;
import com.smartcampus.back.dto.*;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.UserRepository;
import com.smartcampus.back.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 인증 및 회원 관리 서비스
 * 회원가입, 로그인, 이메일 인증, 아이디 찾기, 비밀번호 재설정 등 인증 관련 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailUtil emailUtil;

    // ========================= 1. 회원가입 관련 메서드 ==================================

    /**
     * 아이디 중복 확인
     * @param username 사용자 아이디
     * @return 중복 여부 (true: 중복됨, false: 사용 가능)
     */
    public boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 닉네임 중복 확인
     * @param nickname 사용자 닉네임
     * @return 중복 여부 (true: 중복됨, false: 사용 가능)
     */
    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 이메일 인증번호 발송
     * -> 6자리 코드 생성 후 Redis에 저장 (5분 후 만료)
     * @param email 사용자 이메일
     */
    public void sendVerificationCode(String email) {
        String verificationCode = emailUtil.generateVerificationCode();
        redisTemplate.opsForValue().set(email, verificationCode, 5, TimeUnit.MINUTES);
        emailUtil.sendVerificationEmail(email, verificationCode);
    }

    /**
     * 이메일 인증번호 검증
     * -> Redis에 저장된 인증번호와 비교하여 검증
     * @param request 이메일 인증 요청 (이메일, 인증번호)
     */
    public void verifyEmail(EmailVerificationRequest request) {
        String storedCode = redisTemplate.opsForValue().get(request.getEmail());
        if(storedCode == null || !storedCode.equals(request.getCode())) {
             throw new IllegalArgumentException("유효하지 않은 인증번호입니다.");
        }
    }

    /**
     * 회원가입 처리
     * -> 이메일 인증 여부 확인 후 회원가입 진행
     * -> 비밀번호는 BCrypt 암호화하여 저장
     * @param request 회원가입 요청 DTO
     */
    public void register(RegisterRequest request) {
        if(!userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .email(request.getEmail())
                .role("user")
                .build();
        userRepository.save(user);
    }

    // ========================= 2. 로그인 관련 메서드 ==================================

    /**
     * 로그인 처리
     * -> 아이디와 비밀번호를 검증 후 JWT 발급
     * @param request 로그인 요청 DTO
     * @return Access Token, Refresh Token, 사용자 정보 반환
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호르 잘못 입력하셨습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(user.getUsername());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        redisTemplate.opsForValue().set(user.getUsername(), accessToken, 1, TimeUnit.HOURS);
        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        userRepository.save(user);

        return new LoginResponse(accessToken, refreshToken, user.getUsername(), user.getEmail(), user.getNickname());
    }

    public void kakaoLogin() {
        throw new UnsupportedOperationException("카카오 로그인 기능은 아직 구현되지 않았습니다.");
    }

    public void googleLogin() {
        throw new UnsupportedOperationException("구글 로그인 기능은 아직 구현되지 않았습니다.");
    }

    // ========================= 3. 아이디 찾기 관련 메서드 ==================================

    /**
     * 아이디 찾기
     * -> 이메일을 통해 아이디 찾기
     * @param request
     * @return
     */
    public UsernameFindResponse findUsername(UsernameFindRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 사용자를 찾을 수 없습니다."));
        return new UsernameFindResponse(true, user.getUsername(), "아이디 찾기에 성공했습니다.");
    }

    // ========================= 4. 비밀번호 재설정 관련 메서드 ==================================

    /**
     * 비밀번호 재설정 (이메일 인증 후 비밀번호 변경)
     * -> 이메일 인증 완료 후 비밀번호 변경 진행
     * @param request 비밀번호 재설정 요청 DTO
     * @return 재설정 결과 응답 DTO
     */
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return new ResetPasswordResponse(true, "비밀번호가 성공적으로 변경되었습니다.");
    }
}
