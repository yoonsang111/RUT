package com.stream.tour.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * swagger 설정 파일
 */
//@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("TOUR STREAM API")
                .version(springdocVersion)
                .description("TOUR STREAM API 연동규격서 내용입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
