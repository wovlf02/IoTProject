package com.smartcampus.back.service;

import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 사용자 정보 관리 서비스
 * 사용자 프로필 조회, 수정, 탈퇴 등을 처리
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ========================= 1. 사용자 정보 조회 ==================================

    /**
     * 특정 사용자 정보 조회
     * @param userId 조회할 사용자 ID
     * @return 사용자 정보 DTO
     */
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        return new UserResponse(user);
    }

    /**
     * 내 프로필 조회
     * @param username 로그인한 사용자 아이디
     * @return 사용자 정보 DTO
     */
    public UserResponse getMyProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        return new UserResponse(user);
    }

    // ========================= 2. 사용자 정보 수정 ==================================

    /**
     * 닉네임 변경
     * @param request 닉네임 변경 요청 DTO
     * @return 변경된 사용자 정보 DTO
     */
    public UserResponse updateNickname(UpdateNicknameRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        if(userRepository.existsByNickname(request.getNewNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        user.setNickname(request.getNewNickname());
        userRepository.save(user);

        return new UserResponse(user);
    }

    /**
     * 비밀번호 변경
     * @param request 비밀번호 변경 요청 DTO
     * @return 변경 결과 응답 DTO
     */
    public PasswordChangeResponse changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return new PasswordChangeResponse(true, "비밀번호가 성공적으로 변경되었습니다.");
    }

    // ========================= 3. 프로필 이미지 관리 ==================================

    /**
     * 프로필 이미지 업로드
     * @param username 사용자 아이디
     * @param file 업로드할 프로필 이미지 파일
     * @return 업로드 성공 메시지
     */
    public String uploadProfileImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        try {
            user.setProfileImage(file.getBytes());
            userRepository.save(user);
            return "프로필 이미지가 업로드되었습니다";
        } catch(IOException e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        }
    }

    /**
     * 프로필 이미지 삭제
     * @param username 사용자 아이디
     * @return 삭제 성공 메시지
     */
    public String deleteProfileImage(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        user.setProfileImage(null);
        userRepository.save(user);

        return "프로필 이미지가 삭제되었습니다.";
    }

    // ========================= 4. 회원 탈퇴 ==================================

    public String deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        userRepository.delete(user);

        return "회원 탈퇴가 완료되었습니다.";
    }
}
