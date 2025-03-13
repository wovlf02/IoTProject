package com.smartcampus.back.service;

import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 사용자 관리 서비스
 *
 * 사용자 정보 조회 (마이페이지)
 * 프로필 수정 (닉네임, 이메일)
 * 비밀번호 변경
 * 회원 탈퇴
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================== 사용자 정보 조회 ==============================

    /**
     * 사용자 정보 조회 (마이페이지)
     * 사용자 ID를 기준으로 회원 정보를 조회한다.
     * @param userId 사용자 ID
     * @return 사용자 프로필 정보 (닉네임, 이메일, 가입일 등)
     */
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserProfileResponse(user.getUsername(), user.getNickname(), user.getEmail(), user.getCreatedAt());
    }

    // =========================== 사용자 정보 수정 ==============================

    /**
     * 닉네임 변경
     * 사용자가 입력한 닉네임이 기존에 사용 중인지 확인 후 업데이트한다.
     * @param userId 사용자 ID
     * @param request 닉네임 변경 요청 DTO
     * @return 변경된 닉네임
     */
    public UserProfileResponse updateNickname(Long userId, UpdateNicknameRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        user.setNickname(request.getNickname());
        userRepository.save(user);

        return new UserProfileResponse(user.getUsername(), user.getNickname(), user.getEmail(), user.getCreatedAt());
    }

    /**
     * 이메일 변경 (이메일 인증 필수)
     * 사용자가 입력한 이메일이 기존에 사용 중인지 확인 후 업데이트한다.
     * @param userId 사용자 ID
     * @param request 이메일 변경 요청 DTO
     * @return 변경된 이메일
     */
    public UserProfileResponse updateEmail(Long userId, UpdateEmailRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        user.setEmail(request.getEmail());
        userRepository.save(user);

        return new UserProfileResponse(user.getUsername(), user.getNickname(), user.getEmail(), user.getCreatedAt());
    }

    /**
     * 프로필 이미지 변경
     * 사용자가 프로필 이미지를 업로드하여 변경할 수 있다.
     * @param userId 사용자 ID
     * @param profileImage 업로드된 프로필 이미지 파일
     */
    public void updateProfileImage(Long userId, MultipartFile profileImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        try {
            user.setProfileImage(profileImage.getBytes());
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 저장 중 오류 발생");
        }
    }

    // =========================== 비밀번호 변경 ==============================

    /**
     * 비밀번호 변경
     * 사용자가 기존 비밀번호를 입력한 후, 새로운 비밀번호로 변경할 수 있다.
     * @param userId 사용자 ID
     * @param request 비밀번호 변경 요청 DTO
     */
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // =========================== 회원 탈퇴 ==============================

    /**
     * 회원 탈퇴
     * 사용자가 회원 탈퇴를 요청하면 계정을 삭제한다.
     * @param userId 사용자 ID
     */
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        userRepository.deleteById(userId);
    }
}
