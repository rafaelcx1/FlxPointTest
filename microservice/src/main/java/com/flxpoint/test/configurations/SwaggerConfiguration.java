package com.flxpoint.test.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(apiInfo());
    }

    @Bean
    public GroupedOpenApi apis() {
        return GroupedOpenApi.builder()
                .group("apis")
                .packagesToScan("com.flxpoint.test")
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("FlxPoint")
                .description("FlxPoint - Test")
                .version("v1.0.0");
    }

}
