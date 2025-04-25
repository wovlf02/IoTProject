package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.PasswordChangeRequest;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 공통 서비스
 * <p>
 * 현재 사용자 조회, ID/username/email/nickname 조회, 닉네임 변경, 비밀번호 변경 기능 제공
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 현재 로그인한 사용자 조회
     *
     * @return User 엔티티
     * @throws CustomException 인증 정보 없음 또는 사용자 조회 실패 시
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 ID로 사용자 조회
     *
     * @param userId 조회할 사용자 ID
     * @return User 엔티티
     * @throws CustomException 존재하지 않는 사용자일 경우
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * username(아이디)로 사용자 조회
     *
     * @param username 사용자 아이디
     * @return User 엔티티
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return User 엔티티
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 닉네임으로 사용자 조회
     *
     * @param nickname 사용자 닉네임
     * @return User 엔티티
     */
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 비밀번호 변경
     *
     * @param request PasswordChangeRequest (현재 비밀번호, 새 비밀번호)
     * @throws CustomException 현재 비밀번호가 일치하지 않는 경우
     */
    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        User user = getCurrentUser();

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 새 비밀번호로 변경
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
