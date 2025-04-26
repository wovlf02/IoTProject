package com.smartcampus.back.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Redis 설정 클래스
 * <p>
 * 이메일 인증코드, 토큰 저장 등에 사용되는 Redis 연결 및 템플릿을 구성합니다.
 * </p>
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    /**
     * Redis 서버 연결 설정 (Lettuce)
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /**
     * 문자열 기반 RedisTemplate 등록
     * <p> 인증코드, 리프레시 토큰 등 문자열 데이터 저장에 사용됩니다. </p>
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
