package com.smartcampus.back.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PasswordUtil
 * <p>
 * 비밀번호 암호화 및 검증을 수행하는 유틸리티 클래스입니다.
 * </p>
 */
@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 비밀번호를 암호화합니다.
     *
     * @param rawPassword 사용자가 입력한 평문 비밀번호
     * @return 암호화된 비밀번호
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 입력한 비밀번호가 암호화된 비밀번호와 일치하는지 검증합니다.
     *
     * @param rawPassword 사용자가 입력한 평문 비밀번호
     * @param encodedPassword 저장된 암호화된 비밀번호
     * @return 비밀번호 일치 여부
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
