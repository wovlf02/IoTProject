package com.smartcampus.back.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 인증번호, 임시 비밀번호 등 다양한 랜덤 문자열을 생성하는 유틸리티 클래스입니다.
 */
public class RandomCodeUtil {

    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 6자리 숫자형 인증 코드 생성 (이메일 인증 등에 사용)
     *
     * @return 예: "483927"
     */
    public static String generate6DigitCode() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }
        return sb.toString();
    }

    /**
     * 지정된 길이의 숫자 코드 생성
     *
     * @param length 생성할 자리 수
     * @return 예: "1498231"
     */
    public static String generateNumericCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }
        return sb.toString();
    }

    /**
     * 영문자 + 숫자 조합의 임시 비밀번호 생성
     *
     * @param length 생성할 길이 (예: 10)
     * @return 예: "aF8G9kT2Lp"
     */
    public static String generateTempPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    /**
     * 랜덤 닉네임 예시 생성기 (prefix + 숫자)
     *
     * @param prefix 닉네임 접두어 (예: "user")
     * @return 예: "user8392"
     */
    public static String generateNickname(String prefix) {
        return prefix + RANDOM.nextInt(9000) + 1000;
    }
}
