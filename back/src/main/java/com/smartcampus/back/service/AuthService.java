//package com.smartcampus.back.service;
//
//import com.smartcampus.back.config.JwtProvider;
//import com.smartcampus.back.dto.*;
//import com.smartcampus.back.entity.User;
//import com.smartcampus.back.repository.UserRepository;
//import com.smartcampus.back.utils.EmailUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 인증 및 회원 관리 서비스
// *
// * 회원가입 (아이디/닉네임 중복 확인, 이메일 인증, 비밀번호 강도 검사
// * 로그인 (JWT 발급, 아이디/비밀번호 검증)
// * 이메일 인증 (이메일 인증번호 발송 및 검증)
// * 아이디 찾기 (이메일 인증 후 아이디 제공)
// * 비밀번호 재설정 (이메일 인증 후 비밀번호 변경)
// */
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtProvider jwtProvider;
//    private final RedisTemplate<String, String> redisTemplate;
//    private final EmailUtil emailUtil;
//
//    // =========================== 회원가입 ==============================
//
//    /**
//     * 아이디 중복 확인 (register01)
//     * 사용자가 입력한 아이디가 기존에 존재하는지 확인한다.
//     * @param username 아이디
//     * @return true(중복) / false(사용 가능)
//     */
//    public boolean checkUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    /**
//     * 닉네임 중복 확인 (register06)
//     * 사용자가 입력한 닉네임이 기존에 존재하는지 확인한다.
//     * @param nickname 닉네임
//     * @return true(중복) / false(사용 가능)
//     */
//    public boolean checkNickname(String nickname) {
//        return userRepository.existsByNickname(nickname);
//    }
//
//    /**
//     * 비밀번호 강도 검사 (register03)
//     * 비밀번호가 숫자, 소문자, 대문자, 특수문자를 포함하는지 검증한다.
//     * @param password 비밀번호
//     * @return true(강도 만족) / false(불만족)
//     */
//    public boolean validatePasswordStrength(String password) {
//        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");
//    }
//
//    /**
//     * 이메일 인증번호 발송 (register09)
//     * 사용자 이메일로 6자리 인증번호를 발송한다.
//     * Redis에 저장 후 5분 후 만료
//     * @param email 이메일 주소
//     */
//    public void sendVerificationEmail(String email) {
//        String verificationCode = emailUtil.generateVerificationCode();
//        redisTemplate.opsForValue().set(email, verificationCode, 5, TimeUnit.MINUTES);
//        emailUtil.sendVerificationEmail(email, verificationCode);
//    }
//
//    /**
//     * 이메일 인증번호 검증 (register11)
//     * 사용자가 입력한 인증번호와 Redis에 저장된 인증번호를 비교하여 검증한다.
//     * @param request 이메일과 인증번호를 포함한 DTO
//     */
//    public void verifyEmail(EmailVerificationRequest request) {
//        String storedCode = redisTemplate.opsForValue().get(request.getEmail());
//        if(storedCode == null || !storedCode.equals(request.getCode())) {
//            throw new IllegalArgumentException("유효하지 않은 인증번호입니다.");
//        }
//    }
//
//    /**
//     * 회원가입 처리 (register12)
//     * 이메일 인증 완료 여부 확인 후 회원 등록
//     * 비밀번호는 BCrypt 암호화 후 저장
//     * @param request 회원가입 요청 DTO
//     */
//    public void register(RegisterRequest request) {
//        if(!userRepository.existsByEmail(request.getEmail())) {
//            throw new IllegalArgumentException("이메일 인증을 완료해야 합니다.");
//        }
//
//        User user = User.builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .nickname(request.getNickname())
//                .email(request.getEmail())
//                .role("user")
//                .build();
//
//        userRepository.save(user);
//    }
//
//    // =========================== 로그인 ==============================
//
//    /**
//     * 로그인 처리 (Login03)
//     * 아이디 및 비밀번호 검증 후 JWT 발급
//     * Refresh Token은 DB에 저장
//     * @param request 로그인 요청 DTO
//     * @return 로그인 응답 (토큰, 사용자 정보 포함)
//     */
//    public LoginResponse login(LoginRequest request) {
//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new RuntimeException("아이디를 찾을 수 없습니다."));
//
//        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
//
//        String accessToken = jwtProvider.generateAccessToken(user.getUsername());
//        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
//
//        user.setRefreshToken(passwordEncoder.encode(refreshToken));
//        userRepository.save(user);
//
//        return new LoginResponse(accessToken, refreshToken, user.getUsername(), user.getEmail(), user.getNickname());
//    }
//
//    // =========================== 아이디 찾기 ==============================
//
//    public UsernameFindResponse findUsername(UsernameFindRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 계정이 없습니다."));
//
//        return new UsernameFindResponse(true, user.getUsername(), "아이디 찾기 성공");
//    }
//
//    // =========================== 비밀번호 찾기 ==============================
//
//    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
//
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        userRepository.save(user);
//
//        return new ResetPasswordResponse(true, "비밀번호가 성공적으로 변경되었습니다.");
//    }
//}
