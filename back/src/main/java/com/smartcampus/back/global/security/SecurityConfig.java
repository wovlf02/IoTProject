package com.smartcampus.back.global.security;

import com.smartcampus.back.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * <p>
 * JWT 인증 기반 보안 정책을 구성합니다.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * AuthenticationManager Bean 등록
     * <p> 로그인 인증 시 사용됩니다. </p>
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Spring Security 필터 체인 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (JWT 사용 시 필수)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션을 사용하지 않음 (StateLess)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // URL별 인가(허용/차단) 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/auth/**",       // 로그인, 회원가입, 인증 관련 API
                                "/swagger-ui/**",      // Swagger 문서
                                "/v3/api-docs/**"
                        ).permitAll()                // 위 경로들은 인증 없이 접근 허용
                        .requestMatchers(HttpMethod.GET, "/api/community/posts/**").permitAll() // 게시글 조회 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )

                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
