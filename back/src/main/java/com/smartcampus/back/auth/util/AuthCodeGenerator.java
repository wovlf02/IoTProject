package com.smartcampus.back.auth.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 6자리 인증 코드 생성 유틸
 */
@Component
public class AuthCodeGenerator {

    private final Random random = new Random();

    /**
     * 6자리 숫자 인증 코드 생성
     * @return 6자리 문자열
     */
    public String generate6DigitCode() {
        int code = 100_000 + random.nextInt(900_000);
        return String.valueOf(code);
    }
}
