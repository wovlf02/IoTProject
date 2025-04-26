package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import com.smartcampus.back.auth.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserService
 * <p>
 * 사용자(User) 조회 및 수정과 관련된 공통 기능을 제공하는 서비스입니다.
 * (AuthService, NotificationService 등 다양한 서비스에서 호출됩니다.)
 * </p>
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
     * username으로 사용자 조회
     *
     * @param username 로그인 아이디
     * @return 조회된 사용자
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * email로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return 조회된 사용자
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * nickname으로 사용자 조회
     *
     * @param nickname 사용자 닉네임
     * @return 조회된 사용자
     * @throws CustomException 사용자 없음 예외
     */
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 현재 로그인한 사용자 조회
     *
     * @return 현재 로그인된 사용자
     */
    public User getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }
}
