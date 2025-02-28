package com.ourmenu.backend.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${spring.swagger.server-url:http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server().url(serverUrl).description("API Server")))
                .info(new Info().title("ourmenu API 명세서").description("ourmenu 사용되는 API 명세서").version("v2"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("access token", new SecurityScheme()
                                .name("Authorization")
                                .type(Type.APIKEY)
                                .in(In.HEADER)
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("access token")
                );
    }
}