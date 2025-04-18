package com.smartcampus.back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SmartCampus API",
                version = "v1",
                description = "스마트캠퍼스 플랫폼 API 문서",
                contact = @Contact(name = "SmartCampus Backend", email = "admin@smartcampus.com")
        ),
        security = @SecurityRequirement(name = "BearerAuth"),
        servers = @Server(url = "https://api.smartcampus.com") // 개발 중일 경우 http://localhost:8080
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT 인증을 위해 Authorization 헤더에 Bearer {token} 형식으로 입력하세요."
)
public class SwaggerConfig {
    // SpringDoc OpenAPI 3.x 설정은 annotation 기반으로만 충분하며, 별도 Bean 등록은 필요 없습니다.
}
