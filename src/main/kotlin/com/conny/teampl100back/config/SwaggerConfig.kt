package com.conny.teampl100back.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("v1-definition")
            .pathsToMatch("/api/**")
            .build()
    }

    @Bean
    fun springHopOpenApi(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("API DOCUMENT")
                .description("API 명세서")
                .version("v0.0.1")
                .contact(
                    Contact()
                        .name("정무곤")
                        .url("https://github.com/mugon-dev")
                        .email("jmg41490400@gmail.com")
                )
        )
    }
}