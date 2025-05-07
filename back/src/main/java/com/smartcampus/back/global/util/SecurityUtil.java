package com.smartcampus.back.global.util;

import com.smartcampus.back.global.exception.UnauthorizedException;
import com.smartcampus.back.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security에서 인증된 사용자 정보를 가져오는 유틸 클래스입니다.
 */
public class SecurityUtil {

    /**
     * 현재 인증된 사용자의 ID를 반환합니다.
     *
     * @return 사용자 ID
     * @throws UnauthorizedException 인증 정보가 없을 경우
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("인증된 사용자가 없습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getId(); // 사용자 정의 UserDetails에서 ID 추출
        }

        throw new UnauthorizedException("사용자 인증 정보를 확인할 수 없습니다.");
    }
}
