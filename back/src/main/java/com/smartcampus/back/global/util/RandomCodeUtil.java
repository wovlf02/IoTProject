package com.smartcampus.back.global.util;

import java.security.SecureRandom;

/**
 * 랜덤 코드 생성 유틸리티
 * <p>
 * 이메일 인증 코드 등 보안이 필요한 랜덤 숫자 코드를 생성합니다.
 * </p>
 */
public class RandomCodeUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    /**
     * 6자리 랜덤 숫자 코드 생성
     *
     * @return 6자리 숫자로 구성된 인증코드 문자열
     */
    public static String generate6DigitCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int digit = secureRandom.nextInt(10); // 0 ~ 9
            sb.append(digit);
        }
        return sb.toString();
    }
}
