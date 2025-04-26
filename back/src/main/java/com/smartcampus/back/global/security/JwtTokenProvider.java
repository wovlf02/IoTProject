package com.smartcampus.back.global.security;

import com.smartcampus.back.auth.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰 생성 및 검증 클래스
 * <p>
 * Access Token, Refresh Token을 생성하고 검증하는 역할을 합니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKeyPlain; // yml 파일에서 주입받은 시크릿 키 (Base64 인코딩 필요)

    private Key secretKey;

    private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000L; // 1시간 (ms)
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L; // 7일 (ms)

    /**
     * 초기화: 시크릿 키를 Base64 디코딩하여 Key 객체 생성
     */
    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
    }

    /**
     * Access Token 생성
     *
     * @param user 사용자 정보
     * @return JWT Access Token
     */
    public String generateAccessToken(User user) {
        return createToken(user.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    /**
     * Refresh Token 생성
     *
     * @param user 사용자 정보
     * @return JWT Refresh Token
     */
    public String generateRefreshToken(User user) {
        return createToken(user.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

    /**
     * 실제 토큰 생성 로직
     *
     * @param username 사용자 아이디
     * @param validity 토큰 유효 기간
     * @return JWT 문자열
     */
    private String createToken(String username, long validity) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 사용자 아이디(username) 추출
     *
     * @param token JWT 토큰
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰 유효성 검증
     *
     * @param token 검증할 JWT 토큰
     * @return true = 유효, false = 무효
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 토큰 만료
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // 변조된 토큰, 잘못된 서명
            return false;
        }
    }

    /**
     * Claims 파싱 (서명 검증 포함)
     *
     * @param token JWT 토큰
     * @return Claims (payload)
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
