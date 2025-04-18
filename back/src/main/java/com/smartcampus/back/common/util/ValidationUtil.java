package com.smartcampus.back.common.util;

import java.util.regex.Pattern;

/**
 * 이메일, 휴대폰 번호, 닉네임 등 다양한 형식의 유효성을 검사하는 유틸리티 클래스입니다.
 */
public class ValidationUtil {

    // 이메일 형식: example@example.com
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    // 휴대폰 번호 형식: 01012345678 또는 010-1234-5678
    private static final Pattern PHONE_REGEX =
            Pattern.compile("^01[016789]-?(\\d{3,4})-?(\\d{4})$");

    // 닉네임: 한글/영문/숫자 포함, 2~12자
    private static final Pattern NICKNAME_REGEX =
            Pattern.compile("^[가-힣a-zA-Z0-9]{2,12}$");

    // 아이디: 영문자로 시작 + 영문/숫자 5~20자
    private static final Pattern USERNAME_REGEX =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{4,19}$");

    // 비밀번호: 영문, 숫자, 특수문자 포함 최소 8자
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$");

    /**
     * 이메일 형식 유효성 검사
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    /**
     * 휴대폰 번호 유효성 검사
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_REGEX.matcher(phone).matches();
    }

    /**
     * 닉네임 형식 검사
     */
    public static boolean isValidNickname(String nickname) {
        return nickname != null && NICKNAME_REGEX.matcher(nickname).matches();
    }

    /**
     * 사용자 아이디 형식 검사
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_REGEX.matcher(username).matches();
    }

    /**
     * 비밀번호 형식 검사
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_REGEX.matcher(password).matches();
    }
}
