package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 사용자 정보 관련 공통 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 ID로 사용자 조회
     *
     * @param userId 사용자 고유 ID
     * @return 조회된 사용자
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 username 으로 사용자 조회
     *
     * @param username 사용자 아이디
     * @return 사용자 Entity
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return 사용자 Entity
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 닉네임으로 사용자 조회
     *
     * @param nickname 닉네임
     * @return 사용자 Entity
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 프로필 이미지 URL 업데이트
     *
     * @param user 사용자 엔티티
     * @param newProfileImageUrl 새 이미지 URL
     * @return 업데이트된 사용자
     */
    @Transactional
    public User updateProfileImage(User user, String newProfileImageUrl) {
        user.setProfileImage(newProfileImageUrl);
        return userRepository.save(user);
    }

    /**
     * 비밀번호 변경 처리
     *
     * @param user 사용자
     * @param encodedPassword 인코딩된 새 비밀번호
     */
    @Transactional
    public void updatePassword(User user, String encodedPassword) {
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    /**
     * 사용자가 존재하는지 확인 (by username)
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 사용자가 존재하는지 확인 (by email)
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 사용자가 존재하는지 확인 (by nickname)
     */
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 현재 로그인한 사용자 조회
     *
     * @return 로그인한 사용자 Entity
     * @throws CustomException 인증 정보 없음 또는 사용자 정보 없음 예외
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userRepository.findById(userDetails.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        }

        throw new CustomException(ErrorCode.UNAUTHORIZED);
    }
}
