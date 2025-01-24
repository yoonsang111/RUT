package com.stream.tour.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class P6spyLogMessageFormatConfiguration {

    @Value("${spring.profiles.default}")
    private String springProfile;

    @PostConstruct
    public void setLogMessageFormat() {
        if (springProfile != null && springProfile.equalsIgnoreCase("prod")) {
            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfig.class.getName());
        } else {
            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatDevConfig.class.getName());
        }
    }
}