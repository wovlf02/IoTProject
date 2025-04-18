package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰 생성 및 검증 서비스
 *
 * - Access Token / Refresh Token 발급
 * - 유효성 검증 및 사용자 ID 추출
 */
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessTokenExpirationMillis;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpirationMillis;

    private Key key;

    /**
     * Base64로 인코딩된 secretKey를 Key 객체로 변환
     */
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Access Token 생성
     *
     * @param user 사용자 정보
     * @return Access Token 문자열
     */
    public String createAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성
     *
     * @param user 사용자 정보
     * @return Refresh Token 문자열
     */
    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 유효성 검사
     *
     * @param token 검사할 JWT
     * @return 유효하면 true
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * JWT에서 사용자 아이디(username) 추출
     *
     * @param token JWT
     * @return username
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * JWT에서 사용자 ID 추출
     *
     * @param token JWT
     * @return userId
     */
    public Long extractUserId(String token) {
        return ((Number) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("userId")).longValue();
    }
}
