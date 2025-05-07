package com.smartcampus.back.service.auth;

import com.smartcampus.back.dto.auth.request.*;
import com.smartcampus.back.dto.auth.response.LoginResponse;
import com.smartcampus.back.dto.auth.response.MyInfoResponse;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.global.exception.DuplicatedException;
import com.smartcampus.back.global.exception.NotFoundException;
import com.smartcampus.back.global.exception.UnauthorizedException;
import com.smartcampus.back.global.jwt.JwtProvider;
import com.smartcampus.back.global.util.RedisUtil;
import com.smartcampus.back.global.util.SecurityUtil;
import com.smartcampus.back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;

    private static final String EMAIL_PREFIX = "EMAIL_CODE:";
    private static final String RESET_PREFIX = "RESET_CODE:";
    private static final long EMAIL_CODE_EXP_MINUTES = 5;

    /**
     * 아이디 중복 여부 확인
     */
    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    /**
     * 닉네임 중복 여부 확인
     */
    public boolean isNicknameAvailable(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }

    /**
     * 이메일 중복 여부 확인
     */
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    /**
     * 이메일 인증 코드 전송 (임시 코드 저장)
     */
    public String sendVerificationCode(String email) {
        String code = generateCode();
        redisUtil.set(EMAIL_PREFIX + email, code, EMAIL_CODE_EXP_MINUTES, TimeUnit.MINUTES);
        // 실제 서비스에서는 MailService 등을 통해 이메일 전송
        return code;
    }

    /**
     * 이메일 인증 코드 검증
     */
    public boolean verifyEmailCode(String email, String code) {
        String stored = redisUtil.get(EMAIL_PREFIX + email);
        return stored != null && stored.equals(code);
    }

    /**
     * 회원가입
     */
    @Transactional
    public void signup(SignupRequest request) {
        if (!isUsernameAvailable(request.getUsername())) throw new DuplicatedException("이미 사용 중인 아이디입니다.");
        if (!isNicknameAvailable(request.getNickname())) throw new DuplicatedException("이미 사용 중인 닉네임입니다.");
        if (!isEmailAvailable(request.getEmail())) throw new DuplicatedException("이미 사용 중인 이메일입니다.");

        User user = User.builder()
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role("USER")
                .status("ACTIVE")
                .registeredAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        // request.getTimetable() 은 별도 ScheduleService 등에서 처리
    }

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameAndStatus(request.getUsername(), "ACTIVE")
                .orElseThrow(() -> new UnauthorizedException("아이디 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        redisUtil.set("REFRESH_TOKEN:" + user.getId(), refreshToken, 14, TimeUnit.DAYS);
        user.setLastLoginAt(LocalDateTime.now());

        return new LoginResponse(accessToken, refreshToken);
    }

    /**
     * 로그아웃
     */
    public void logout(String accessToken) {
        Long userId = jwtProvider.getUserIdFromToken(accessToken);
        redisUtil.delete("REFRESH_TOKEN:" + userId);
    }

    /**
     * 비밀번호 재설정 인증 코드 발송
     */
    public String sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 이메일입니다."));

        String code = generateCode();
        redisUtil.set(RESET_PREFIX + email, code, EMAIL_CODE_EXP_MINUTES, TimeUnit.MINUTES);
        return code;
    }

    /**
     * 비밀번호 재설정
     */
    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        String stored = redisUtil.get(RESET_PREFIX + request.getEmail());
        if (stored == null || !stored.equals(request.getCode())) {
            throw new UnauthorizedException("인증 코드가 일치하지 않습니다.");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    /**
     * 내 정보 조회
     */
    public MyInfoResponse getMyInfo() {
        Long id = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        return MyInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    /**
     * 내 정보 수정
     */
    @Transactional
    public void updateMyInfo(MyInfoUpdateRequest request) {
        Long id = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
    }

    /**
     * 회원 탈퇴 (계정 상태 변경)
     */
    @Transactional
    public void deleteMyAccount() {
        Long id = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        user.setStatus("WITHDRAW");
    }

    /**
     * 인증 코드 생성 (6자리 랜덤)
     */
    private String generateCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }
}
