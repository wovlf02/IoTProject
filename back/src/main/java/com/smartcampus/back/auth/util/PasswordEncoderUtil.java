package com.smartcampus.back.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화 및 검증을 위한 유틸리티 클래스
 *
 * - BCryptPasswordEncoder를 사용하여 안전한 해싱 제공
 */
@Component
public class PasswordEncoderUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 비밀번호를 해싱합니다.
     *
     * @param rawPassword 평문 비밀번호
     * @return 암호화된 비밀번호 (BCrypt 해시)
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 비밀번호 일치 여부를 비교합니다.
     *
     * @param rawPassword 사용자 입력 평문 비밀번호
     * @param encodedPassword DB에 저장된 해시된 비밀번호
     * @return 일치 여부 (true: 일치함)
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
