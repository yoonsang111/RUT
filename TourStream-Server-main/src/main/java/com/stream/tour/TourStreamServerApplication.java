package com.stream.tour;

import com.stream.tour.global.aop.aspect.ExceptionLogAspect;
import com.stream.tour.global.aop.aspect.RetryAspect;
import com.stream.tour.global.properties.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import({RetryAspect.class, ExceptionLogAspect.class})
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties(value = {
        ApiProperties.class
})
@SpringBootApplication
public class TourStreamServerApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(TourStreamServerApplication.class, args);
    }

}
