package com.smartcampus.back.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 기반 인증 코드 및 RefreshToken 저장/조회/삭제 유틸리티입니다.
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    /**
     * 키-값 저장 (만료 시간 설정)
     */
    public void set(String key, String value, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, duration, unit);
    }

    /**
     * 값 조회
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 키 삭제
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 키 존재 여부 확인
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
