package com.smartcampus.back.global.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(OpenAPI 3) 설정 클래스
 * <p>
 * SmartCampus API 문서를 자동 생성하여 /swagger-ui.html 경로에서 제공합니다.
 * </p>
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 3 문서 정보 정의
     *
     * @return OpenAPI 문서 설정 객체
     */
    @Bean
    public OpenAPI smartCampusOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartCampus API")
                        .description("스마트캠퍼스 백엔드 REST API 명세서")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("SmartCampus Dev Team")
                                .email("support@smartcampus.kr")
                                .url("https://smartcampus.kr"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SmartCampus Docs")
                        .url("https://smartcampus.kr/docs"));
    }
}
