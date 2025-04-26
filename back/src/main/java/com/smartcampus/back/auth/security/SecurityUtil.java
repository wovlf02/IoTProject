package com.smartcampus.back.auth.security;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * SecurityUtil
 * <p>
 * Spring Security Context에서 현재 로그인한 사용자의 User 객체를 가져오는 유틸리티 클래스입니다.
 * </p>
 */
@Component
public class SecurityUtil {

    /**
     * 현재 로그인한 사용자의 User 객체를 반환합니다.
     *
     * @return 현재 로그인된 User
     * @throws CustomException 인증 정보가 없거나 잘못된 경우
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
