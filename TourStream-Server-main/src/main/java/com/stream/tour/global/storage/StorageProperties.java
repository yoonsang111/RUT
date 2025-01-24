package com.stream.tour.global.storage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "storage")
@Validated
public class StorageProperties {

    @Valid
    private Location location;

    @Getter
    public static class Location {
        @NotNull
        private Path product;

        @NotNull
        private Path partner;

        @NotNull
        private String s3;

        public void setProduct(String product) {
            if (StringUtils.isBlank(product)) {
                return;
            }
            this.product = Paths.get(product);
        }

        public void setPartner(String partner) {
            if (StringUtils.isBlank(partner)) {
                return;
            }
            this.partner = Paths.get(partner);
        }

        public void setS3(String s3) {
            this.s3 = s3;
        }
    }
}
