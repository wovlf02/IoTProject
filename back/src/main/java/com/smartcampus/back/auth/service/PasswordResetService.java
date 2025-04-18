package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.ResetPasswordRequest;
import com.smartcampus.back.auth.dto.request.UsernameFindRequest;
import com.smartcampus.back.auth.dto.response.ResetPasswordResponse;
import com.smartcampus.back.auth.dto.response.UsernameFindResponse;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.auth.util.PasswordEncoderUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 아이디 찾기 및 비밀번호 재설정 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    /**
     * 이메일을 기반으로 사용자 아이디(Username)를 반환합니다.
     *
     * @param request 이메일 기반 요청 DTO
     * @return 아이디 응답 DTO
     */
    public UsernameFindResponse findUsername(UsernameFindRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UsernameFindResponse(user.getUsername());
    }

    /**
     * 사용자 인증 후 비밀번호를 재설정합니다.
     *
     * @param request 재설정 요청 DTO (username + email + 새 비밀번호)
     * @return 재설정 완료 메시지
     */
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PASSWORD_RESET_REQUEST));

        String encodedPassword = passwordEncoderUtil.encode(request.getNewPassword());
        user.setPassword(encodedPassword);

        return new ResetPasswordResponse("비밀번호가 성공적으로 변경되었습니다.");
    }
}
