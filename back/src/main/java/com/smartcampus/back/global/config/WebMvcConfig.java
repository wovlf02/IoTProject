package com.smartcampus.back.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 설정 클래스
 * <p>
 * CORS 정책, 정적 리소스 경로, 인터셉터 설정 등을 관리합니다.
 * </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * CORS 설정
     * <p>
     * 모든 출처에 대해 인증 없이 접근 허용 (개발 중 허용 범위 넓게 설정)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로에 대해
                .allowedOrigins("*") // 모든 Origin 허용 (실서비스 시 도메인 제한 권장)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "RefreshToken")
                .allowCredentials(false)
                .maxAge(3600);
    }

    /**
     * 정적 리소스 경로 설정 (Swagger UI 등)
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
