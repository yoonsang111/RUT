package com.stream.tour.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private TourStream tourStream;

    @Setter
    @Getter
    public static class TourStream {
        private String host;
    }

}
