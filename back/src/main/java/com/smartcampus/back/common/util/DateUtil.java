package com.smartcampus.back.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 날짜 및 시간 관련 유틸리티 클래스입니다.
 * <p>주로 포맷 변환, UTC 처리, 만료시간 계산 등에 사용됩니다.</p>
 */
public class DateUtil {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 현재 UTC 시간 반환
     *
     * @return 현재 시간 (UTC 기준)
     */
    public static LocalDateTime nowUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     * 현재 시스템 로컬 시간 반환
     *
     * @return 현재 시간 (Local 기준)
     */
    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    /**
     * LocalDateTime → 문자열 (기본 포맷 yyyy-MM-dd HH:mm:ss)
     */
    public static String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    /**
     * 문자열 → LocalDateTime (기본 포맷 yyyy-MM-dd HH:mm:ss)
     */
    public static LocalDateTime parse(String timeStr) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    /**
     * LocalDateTime → UTC Instant로 변환
     */
    public static Instant toUTCInstant(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC);
    }

    /**
     * 특정 시간 + 만료 시간(ms) 계산 후 반환
     */
    public static LocalDateTime plusMillis(LocalDateTime baseTime, long millis) {
        return baseTime.plusNanos(millis * 1_000_000);
    }

    /**
     * 두 시간 비교: now > expirationTime ?
     */
    public static boolean isExpired(LocalDateTime expirationTime) {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
