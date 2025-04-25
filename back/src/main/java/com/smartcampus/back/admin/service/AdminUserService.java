package com.smartcampus.back.admin.service;

import com.smartcampus.back.admin.dto.request.UserStatusUpdateRequest;
import com.smartcampus.back.admin.dto.response.UserListResponse;
import com.smartcampus.back.admin.repository.AdminUserRepository;
import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 전용 사용자 관리 서비스
 * <p>
 * 사용자 목록 조회, 사용자 상태 변경 등 관리 기능을 담당한다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    /**
     * 사용자 목록 조회 (상태 기반 필터링 포함)
     *
     * @param status   상태 필터 (예: ACTIVE, SUSPENDED). null 이면 전체 조회
     * @param pageable 페이징 정보 객체 (page, size 등)
     * @return UserListResponse 형태의 사용자 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    public UserListResponse getUserList(String status, Pageable pageable) {
        Page<User> userPage;

        if (status == null || status.isBlank()) {
            // 전체 사용자 페이징 조회
            userPage = adminUserRepository.findAll(pageable);
        } else {
            try {
                userPage = adminUserRepository.findUsersByStatus(User.Status.valueOf(status.toUpperCase()), pageable);
            } catch (IllegalArgumentException e) {
                throw new CustomException(ErrorCode.INVALID_USER_STATUS);
            }
        }

        return UserListResponse.fromPage(userPage);
    }

    /**
     * 특정 사용자의 계정 상태를 변경
     *
     * @param userId  상태 변경 대상 사용자 ID
     * @param request 사용자 상태 변경 요청 DTO (변경할 status 필드 포함)
     */
    public void updateUserStatus(Long userId, UserStatusUpdateRequest request) {
        User user = adminUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            User.Status newStatus = User.Status.valueOf(request.getStatus().toUpperCase());
            user.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_USER_STATUS);
        }

        // 변경된 상태는 JPA dirty checking 으로 자동 반영됨
    }
}
