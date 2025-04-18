package com.smartcampus.back.common.security;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰 생성 및 유효성 검증을 수행하는 유틸리티 클래스
 *
 * - AccessToken / RefreshToken 생성
 * - 토큰 유효성 확인 및 사용자 정보 추출
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpirationMillis;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpirationMillis;

    private Key key;

    /**
     * Base64 문자열을 Secret Key로 변환
     */
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Access Token 발급
     *
     * @param user 유저 정보
     * @return Access Token 문자열
     */
    public String createAccessToken(User user) {
        return buildToken(user.getUsername(), user.getId(), user.getRole(), accessTokenExpirationMillis);
    }

    /**
     * Refresh Token 발급
     *
     * @param user 유저 정보
     * @return Refresh Token 문자열
     */
    public String createRefreshToken(User user) {
        return buildToken(user.getUsername(), null, null, refreshTokenExpirationMillis);
    }

    /**
     * 토큰 생성 내부 로직
     */
    private String buildToken(String username, Long userId, String role, long expirationTime) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256);

        if (userId != null) builder.claim("userId", userId);
        if (role != null) builder.claim("role", role);

        return builder.compact();
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token 검사 대상 토큰
     * @return true = 유효, 예외 발생 시 false 처리
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 사용자 아이디 (username) 추출
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 사용자 DB ID 추출
     */
    public Long extractUserId(String token) {
        Object userId = parseClaims(token).get("userId");
        return userId != null ? Long.valueOf(userId.toString()) : null;
    }

    /**
     * JWT Claims 파싱
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에서 사용자 ID 추출 (extractUserId와 동일한 기능)
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long getUserIdFromToken(String token) {
        return extractUserId(token);
    }
}
