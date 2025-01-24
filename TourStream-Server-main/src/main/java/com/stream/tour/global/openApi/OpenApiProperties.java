package com.stream.tour.global.openApi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("open-api")
public class OpenApiProperties {
    private String key;
}
