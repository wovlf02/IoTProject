package com.smartcampus.back.auth.security;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JwtProvider
 * <p>
 * JWT 토큰을 생성, 검증, 파싱하는 기능을 제공합니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKeyString;

    @Value("${jwt.access-token-expire-time}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token-expire-time}")
    private long refreshTokenExpireTime;

    private Key secretKey;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * secretKey 초기화
     */
    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    /**
     * Access Token 생성
     *
     * @param user 사용자 엔티티
     * @return Access Token 문자열
     */
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성
     *
     * @param user 사용자 엔티티
     * @return Refresh Token 문자열
     */
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * HTTP 요청에서 JWT 토큰 추출
     *
     * @param request HTTP 요청
     * @return JWT 토큰 문자열 (없으면 null)
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * JWT 토큰 유효성 검증
     *
     * @param token 검증할 토큰
     * @return 유효한지 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * JWT 토큰에서 사용자 정보(User) 추출
     *
     * @param token JWT 토큰
     * @return User 객체 (username, id, role만 복구 가능)
     */
    public User getUserFromToken(String token) {
        Claims claims = parseClaims(token);

        return User.builder()
                .id(claims.get("userId", Long.class))
                .username(claims.getSubject())
                .role(User.Role.valueOf(claims.get("role", String.class)))
                .status(User.Status.ACTIVE) // 기본 ACTIVE로 설정
                .build();
    }

    /**
     * JWT 토큰 파싱 (claims 추출)
     *
     * @param token JWT 토큰
     * @return Claims
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료된 토큰이어도 claims는 꺼낼 수 있다
        }
    }
}
