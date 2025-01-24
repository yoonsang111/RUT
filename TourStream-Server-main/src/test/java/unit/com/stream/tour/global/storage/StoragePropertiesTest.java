package com.stream.tour.global.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource("classpath:application-dev.yml")
@SpringBootTest
class StoragePropertiesTest {

    @Autowired
    private StorageProperties storageProperties;

    @Test
    public void testStoragePath() throws Exception {
        assertThat("src/main/resources/static/images/product").isEqualTo(storageProperties.getLocation().getProduct().toString());
        assertThat("src/main/resources/static/images/partner").isEqualTo(storageProperties.getLocation().getPartner().toString());
    }

}