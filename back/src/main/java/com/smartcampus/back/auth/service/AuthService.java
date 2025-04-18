package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.LoginRequest;
import com.smartcampus.back.auth.dto.request.NicknameCheckRequest;
import com.smartcampus.back.auth.dto.request.RegisterRequest;
import com.smartcampus.back.auth.dto.request.UsernameCheckRequest;
import com.smartcampus.back.auth.dto.response.LoginResponse;
import com.smartcampus.back.auth.dto.response.NicknameCheckResponse;
import com.smartcampus.back.auth.dto.response.RegisterResponse;
import com.smartcampus.back.auth.dto.response.UsernameCheckResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.auth.util.JwtTokenProvider;
import com.smartcampus.back.auth.util.PasswordEncoderUtil;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 인증 관련 핵심 비즈니스 로직을 처리하는 서비스 클래스
 * -> 회원가입, 로그인, 중복확인, 토큰 발급
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     * @param request 로그인 요청 (아이디, 비밀번호)
     * @return JWT 토큰 및 사용자 정보 포함 응답
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken, user.getNickname(), user.getEmail(), user.getUsername());
    }

    /**
     * 회원가입 처리
     * @param request 회원가입 요청 DTO
     * @param profileImage 프로필 이미지 파일 (선택)
     * @return 가입 완료 응답
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request, MultipartFile profileImage) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if(userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoderUtil.encode(request.getPassword());

        String profileImageUrl = null;
        if(profileImage != null && !profileImage.isEmpty()) {
            // TODO: 파일 저장 처리 후 경로 반환
            profileImageUrl = "/uploads/profile/" + profileImage.getOriginalFilename();
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImage(profileImageUrl)
                .build();

        User savedUser = userRepository.save(newUser);

        return new RegisterResponse(savedUser.getId(), "회원가입이 완료되었습니다.");
    }

    /**
     * 아이디 중복 확인
     * @param request 아이디 요청 DTO
     * @return 사용 가능 여부
     */
    public UsernameCheckResponse checkUsername(UsernameCheckRequest request) {
        boolean available = !userRepository.existsByUsername(request.getUsername());
        return new UsernameCheckResponse(available);
    }

    /**
     * 닉네임 중복 확인
     * @param request 닉네임 요청 DTO
     * @return 사용 가능 여부
     */
    public NicknameCheckResponse checkNickname(NicknameCheckRequest request) {
        boolean available = !userRepository.existsByNickname(request.getNickname());
        return new NicknameCheckResponse(available);
    }
}
